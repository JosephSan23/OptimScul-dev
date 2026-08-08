package backend.community.application.usecase.EstudianteCrud;

import backend.community.application.port.CredencialesEstudianteRepository;
import backend.community.application.port.DatosCredencialEstudiante;
import backend.notification.application.port.EmailPort;
import backend.people.application.port.EstudianteRepository;
import backend.people.domain.model.Estudiante;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EnviarCredencialesEstudianteUseCase {

    private final CredencialesEstudianteRepository credencialesRepository;
    private final EstudianteRepository estudianteRepository;
    private final EmailPort emailPort;
    private final AutorizacionService auth;

    public EnviarCredencialesEstudianteUseCase(CredencialesEstudianteRepository credencialesRepository,
            EstudianteRepository estudianteRepository, EmailPort emailPort, AutorizacionService auth) {
        this.credencialesRepository = credencialesRepository;
        this.estudianteRepository = estudianteRepository;
        this.emailPort = emailPort;
        this.auth = auth;
    }

    public enum Estado {
        ENVIADO_ACUDIENTE,   // llegó al correo del acudiente (con credenciales del estudiante y del acudiente)
        ENVIADO_ESTUDIANTE,  // no hay acudiente con correo; llegó al correo propio del estudiante
        SIN_CORREO,          // ni el principal ni el estudiante tienen correo → entregar por otro medio
        YA_ACTIVADO,         // la cuenta destino ya inició sesión → no se reenvía (usar recuperar contraseña)
        CORREO_DESHABILITADO // el servidor de correo no está habilitado o falló el envío
    }

    public record Resultado(Estado estado, String destino) {
    }

    @Transactional(readOnly = true)
    public Resultado ejecutar(UUID coordId, UUID estudianteId) {
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");

        Estudiante est = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        if (!inst.equals(est.getInstitucionId()))
            throw new SecurityException("El estudiante es de otra institución.");

        DatosCredencialEstudiante d = credencialesRepository.obtener(estudianteId)
                .orElseThrow(() -> new RuntimeException("No se encontraron los datos del estudiante."));

        boolean tieneAcudiente = d.acudienteUsername() != null;
        String correoAcudiente = tieneAcudiente ? limpiar(d.acudienteCorreo()) : null;
        String correoEstudiante = limpiar(d.estudianteCorreo());

        // Destino preferente: el acudiente principal. Si no tiene correo, el estudiante.
        String destino;
        Estado ok;
        boolean pendiente;
        if (correoAcudiente != null) {
            destino = correoAcudiente;
            ok = Estado.ENVIADO_ACUDIENTE;
            pendiente = d.acudientePendiente();
        } else if (correoEstudiante != null) {
            destino = correoEstudiante;
            ok = Estado.ENVIADO_ESTUDIANTE;
            pendiente = d.estudiantePendiente();
        } else {
            return new Resultado(Estado.SIN_CORREO, null);
        }

        // Solo se envían credenciales a cuentas que aún no han iniciado sesión.
        if (!pendiente) {
            return new Resultado(Estado.YA_ACTIVADO, null);
        }

        String titulo = "Credenciales de acceso · OptimScul";
        String mensaje = componerMensaje(d, ok);

        boolean enviado = emailPort.enviarDirecto(destino, titulo, mensaje);
        return new Resultado(enviado ? ok : Estado.CORREO_DESHABILITADO, enviado ? destino : null);
    }

    private String componerMensaje(DatosCredencialEstudiante d, Estado destino) {
        StringBuilder sb = new StringBuilder();

        if (destino == Estado.ENVIADO_ACUDIENTE) {
            sb.append("Estimado(a) ").append(d.acudienteNombre()).append(",\n\n");
            sb.append("Le compartimos las credenciales de acceso a la plataforma OptimScul.\n");
            sb.append("La contraseña temporal es el número de documento de cada persona; ")
              .append("se le pedirá cambiarla en el primer ingreso.\n\n");

            sb.append("• Acceso del estudiante ").append(d.estudianteNombre()).append(":\n");
            sb.append("   Usuario: ").append(d.estudianteUsername()).append("\n");
            sb.append("   Contraseña: número de documento del estudiante\n\n");

            sb.append("• Su acceso como acudiente:\n");
            sb.append("   Usuario: ").append(d.acudienteUsername()).append("\n");
            sb.append("   Contraseña: su número de documento\n\n");
        } else {
            sb.append("Estimado(a) ").append(d.estudianteNombre()).append(",\n\n");
            sb.append("Le compartimos sus credenciales de acceso a la plataforma OptimScul.\n");
            sb.append("La contraseña temporal es su número de documento; ")
              .append("se le pedirá cambiarla en el primer ingreso.\n\n");
            sb.append("   Usuario: ").append(d.estudianteUsername()).append("\n");
            sb.append("   Contraseña: su número de documento\n\n");
        }

        sb.append("Por seguridad, no comparta estas credenciales.\n\n");
        sb.append("Equipo OptimScul");
        return sb.toString();
    }

    private String limpiar(String correo) {
        if (correo == null)
            return null;
        String c = correo.trim();
        return c.isEmpty() ? null : c;
    }
}
