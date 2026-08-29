package backend.busqueda.application;

import backend.academic.application.port.AsignaturaRepository;
import backend.busqueda.infrastructure.persistence.EstudianteBusquedaJpaRepository;
import backend.security.application.AutorizacionService;
import backend.staff.application.port.StaffConsultaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Buscador global de la cabecera. Busca dentro de la institución del usuario
 * autenticado. El contenido depende del rol:
 *   - Coordinador académico: estudiantes (alumnos) + asignaturas.
 *   - Administrador de institución: personal (docentes/staff) + asignaturas.
 */
@Service
public class BusquedaService {

    private static final int LIMITE = 6;       // máx. resultados por tipo
    private static final int MIN_LONGITUD = 2; // no busca con menos de 2 caracteres

    private final AutorizacionService autorizacion;
    private final EstudianteBusquedaJpaRepository estudianteBusqueda;
    private final AsignaturaRepository asignaturaRepository;
    private final StaffConsultaRepository staffConsulta;

    public BusquedaService(AutorizacionService autorizacion,
                           EstudianteBusquedaJpaRepository estudianteBusqueda,
                           AsignaturaRepository asignaturaRepository,
                           StaffConsultaRepository staffConsulta) {
        this.autorizacion = autorizacion;
        this.estudianteBusqueda = estudianteBusqueda;
        this.asignaturaRepository = asignaturaRepository;
        this.staffConsulta = staffConsulta;
    }

    public Resultado buscar(UUID usuarioId, String termino) {
        String q = termino == null ? "" : termino.trim();
        if (q.length() < MIN_LONGITUD) return Resultado.vacio();

        UUID inst = autorizacion.institucionActual(usuarioId);

        // Solo coordinador y admin pueden usar el buscador (evita exponer
        // datos de personas a docentes, estudiantes o acudientes).
        boolean esCoordinador = autorizacion.tieneRolEnInstitucion(usuarioId, inst, "COORDINADOR_ACADEMICO");
        boolean esAdmin = autorizacion.tieneRolEnInstitucion(usuarioId, inst, "ADMIN_INSTITUCION");
        if (!esCoordinador && !esAdmin) {
            throw new SecurityException("No tienes permisos para usar el buscador.");
        }

        String like = "%" + q + "%";
        String qLower = q.toLowerCase();

        // Estudiantes: solo el coordinador (es quien tiene la vista de detalle).
        List<ItemEstudiante> estudiantes = esCoordinador
                ? estudianteBusqueda.buscar(inst.toString(), like, PageRequest.of(0, LIMITE)).stream()
                    .map(e -> new ItemEstudiante(e.getId(), e.getNombre(), e.getCodigoEstudiante(), e.getNumeroDocumento()))
                    .toList()
                : List.of();

        // Personal: solo el admin (es quien tiene la vista de detalle de staff).
        List<ItemPersonal> personal = esAdmin
                ? staffConsulta.listarPorInstitucion(inst).stream()
                    .filter(s -> contiene(nombreCompleto(s.getPrimerNombre(), s.getPrimerApellido()), qLower)
                              || contiene(s.getNumeroDocumento(), qLower))
                    .limit(LIMITE)
                    .map(s -> new ItemPersonal(s.getUsuarioId(),
                            nombreCompleto(s.getPrimerNombre(), s.getPrimerApellido()),
                            s.getRolNombre(), s.getNumeroDocumento()))
                    .toList()
                : List.of();

        // Asignaturas: coordinador y admin.
        List<ItemAsignatura> asignaturas = asignaturaRepository.findByInstitucionId(inst).stream()
                .filter(a -> contiene(a.getNombre(), qLower) || contiene(a.getCodigo(), qLower))
                .limit(LIMITE)
                .map(a -> new ItemAsignatura(a.getId(), a.getNombre(), a.getCodigo()))
                .toList();

        return new Resultado(estudiantes, personal, asignaturas);
    }

    private boolean contiene(String valor, String qLower) {
        return valor != null && valor.toLowerCase().contains(qLower);
    }

    private String nombreCompleto(String nombre, String apellido) {
        return ((nombre == null ? "" : nombre) + " " + (apellido == null ? "" : apellido)).trim();
    }

    public record ItemEstudiante(UUID id, String nombre, String codigo, String documento) {}
    public record ItemPersonal(UUID id, String nombre, String rol, String documento) {}
    public record ItemAsignatura(UUID id, String nombre, String codigo) {}
    public record Resultado(List<ItemEstudiante> estudiantes, List<ItemPersonal> personal, List<ItemAsignatura> asignaturas) {
        static Resultado vacio() { return new Resultado(List.of(), List.of(), List.of()); }
    }
}
