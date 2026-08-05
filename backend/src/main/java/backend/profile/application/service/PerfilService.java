package backend.profile.application.service;

import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Persona;
import backend.people.domain.model.Sexo;
import backend.profile.application.PerfilVista;
import backend.profile.application.port.ProfesorPerfilRepository;
import backend.profile.infrastructure.rest.dto.ActualizarPerfilRequest;
import backend.profile.infrastructure.rest.dto.CambiarPasswordRequest;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.Usuario;
import backend.shared.application.port.StoragePort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PerfilService {

    private static final Set<String> MIME_IMAGEN = Set.of("image/png", "image/jpeg", "image/webp");
    private static final long MAX_FOTO_BYTES = 5L * 1024 * 1024; // 5 MB

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final ProfesorPerfilRepository profesorPerfil;
    private final PasswordEncoder passwordEncoder;
    private final StoragePort storage;

    public PerfilService(UsuarioRepository usuarioRepository, PersonaRepository personaRepository,
                         ProfesorPerfilRepository profesorPerfil, PasswordEncoder passwordEncoder,
                         StoragePort storage) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.profesorPerfil = profesorPerfil;
        this.passwordEncoder = passwordEncoder;
        this.storage = storage;
    }

    @Transactional(readOnly = true)
    public PerfilVista obtener(UUID usuarioId) {
        Usuario u = usuario(usuarioId);
        Persona p = persona(u);
        var extras = profesorPerfil.leer(p.getId());
        List<String> faltantes = camposFaltantes(p);

        return new PerfilVista(
                p.getTipoDocumento() != null ? p.getTipoDocumento().name() : null, p.getNumeroDocumento(),
                p.getPrimerNombre(), p.getPrimerApellido(),
                p.getSegundoNombre(), p.getSegundoApellido(), p.getFechaNacimiento(),
                p.getSexo() != null ? p.getSexo().name() : null, p.getNacionalidad(),
                p.getTelefono(), p.getTelefonoAlternativo(), p.getCorreo(),
                p.getDireccion(), p.getBarrio(), p.getCiudad(), p.getDepartamento(), p.getPais(),
                urlFoto(p.getFotoUrl()),
                extras.isPresent(),
                extras.map(ProfesorPerfilRepository.Extras::especialidad).orElse(null),
                extras.map(ProfesorPerfilRepository.Extras::tituloProfesional).orElse(null),
                u.getUsername(),
                Boolean.TRUE.equals(u.getRequiereCambioPassword()),
                faltantes.isEmpty(), faltantes);
    }

    @Transactional
    public PerfilVista actualizar(UUID usuarioId, ActualizarPerfilRequest req) {
        Usuario u = usuario(usuarioId);
        Persona p = persona(u);

        // identidad (documento y nombres) NO se toca aquí
        p.setSegundoNombre(req.segundoNombre());
        p.setSegundoApellido(req.segundoApellido());
        p.setFechaNacimiento(req.fechaNacimiento());
        p.setSexo(req.sexo() != null && !req.sexo().isBlank() ? Sexo.valueOf(req.sexo()) : null);
        p.setNacionalidad(req.nacionalidad());
        p.setTelefono(req.telefono());
        p.setTelefonoAlternativo(req.telefonoAlternativo());
        p.setCorreo(req.correo());
        p.setDireccion(req.direccion());
        p.setBarrio(req.barrio());
        p.setCiudad(req.ciudad());
        p.setDepartamento(req.departamento());
        p.setPais(req.pais());
        p.setUpdatedAt(LocalDateTime.now());
        personaRepository.save(p);

        // extras solo si es docente (tiene fila en profesor)
        if (profesorPerfil.leer(p.getId()).isPresent()) {
            profesorPerfil.actualizar(p.getId(), req.especialidad(), req.tituloProfesional());
        }
        return obtener(usuarioId);
    }

    @Transactional
    public void cambiarPassword(UUID usuarioId, CambiarPasswordRequest req) {
        Usuario u = usuario(usuarioId);
        if (!passwordEncoder.matches(req.actual(), u.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña actual no es correcta");
        }
        if (passwordEncoder.matches(req.nueva(), u.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La nueva contraseña no puede ser igual a la actual");
        }
        u.setPasswordHash(passwordEncoder.encode(req.nueva()));
        u.setRequiereCambioPassword(false);
        u.setUltimoCambioPassword(LocalDateTime.now());
        u.setUpdatedAt(LocalDateTime.now());
        usuarioRepository.save(u);
    }

    @Transactional
    public String actualizarFoto(UUID usuarioId, MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Archivo vacío");
        String mime = archivo.getContentType();
        if (mime == null || !MIME_IMAGEN.contains(mime))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permiten imágenes PNG, JPG o WEBP");
        if (archivo.getSize() > MAX_FOTO_BYTES)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La imagen supera los 5 MB");

        Usuario u = usuario(usuarioId);
        Persona p = persona(u);
        String ext = switch (mime) { case "image/png" -> ".png"; case "image/webp" -> ".webp"; default -> ".jpg"; };
        String clave = "perfil/" + usuarioId + "/foto" + ext;
        try {
            storage.subir(clave, archivo.getInputStream(), archivo.getSize(), mime);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo subir la imagen");
        }
        p.setFotoUrl(clave);                 // guardamos la CLAVE; la URL se prefirma al leer
        p.setUpdatedAt(LocalDateTime.now());
        personaRepository.save(p);
        return urlFoto(clave);
    }

    // ---- helpers ----
    private Usuario usuario(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }
    private Persona persona(Usuario u) {
        return personaRepository.findById(u.getPersonaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
    }
    private String urlFoto(String clave) {
        if (clave == null || clave.isBlank()) return null;
        return storage.generarUrlDescarga(clave, Duration.ofMinutes(15));
    }
    private List<String> camposFaltantes(Persona p) {
        List<String> f = new ArrayList<>();
        if (p.getFechaNacimiento() == null) f.add("fechaNacimiento");
        if (p.getSexo() == null) f.add("sexo");
        if (esVacio(p.getTelefono())) f.add("telefono");
        if (esVacio(p.getDireccion())) f.add("direccion");
        if (esVacio(p.getCiudad())) f.add("ciudad");
        return f;
    }
    private boolean esVacio(String s) { return s == null || s.isBlank(); }
}