package backend.security.application;

import backend.security.application.port.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.UUID;

@Service
public class UsernameGenerator {

    private final UsuarioRepository usuarioRepository;

    public UsernameGenerator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String generar(String primerNombre, String primerApellido, String numeroDocumento) {
        return generar(primerNombre, primerApellido, numeroDocumento, null);
    }

    /** usuarioId != null (edición): permite conservar el username propio sin chocar consigo mismo. */
    public String generar(String primerNombre, String primerApellido, String numeroDocumento, UUID usuarioId) {
        String base = normalizar(primerNombre) + "-" + normalizar(primerApellido) + dosDigitos(numeroDocumento);
        String candidato = base;
        int intento = 1;
        while (true) {
            var existente = usuarioRepository.findByUsernameOrEmail(candidato);
            if (existente.isEmpty() || (usuarioId != null && existente.get().getId().equals(usuarioId))) break;
            candidato = base + "-" + (intento++);
        }
        return candidato;
    }

    private String dosDigitos(String doc) {
        String d = doc.replaceAll("\\D", "");
        return d.length() >= 2 ? d.substring(0, 2) : d;
    }

    private String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase().replaceAll("[^a-z0-9]", "");
    }
}