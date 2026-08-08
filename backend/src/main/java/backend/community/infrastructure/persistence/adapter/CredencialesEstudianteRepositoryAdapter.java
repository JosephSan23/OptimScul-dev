package backend.community.infrastructure.persistence.adapter;

import backend.community.application.port.CredencialesEstudianteRepository;
import backend.community.application.port.DatosCredencialEstudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CredencialesEstudianteRepositoryAdapter implements CredencialesEstudianteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<DatosCredencialEstudiante> obtener(UUID estudianteId) {
        // El destino de las credenciales es SIEMPRE el acudiente principal
        // (es el único con cuenta). Si el principal no tiene correo, el caso de
        // uso cae al correo del propio estudiante. Los flags "pendiente" indican
        // que la cuenta aún no ha iniciado sesión (ultimo_login IS NULL).
        List<Object[]> filas = em.createNativeQuery("""
                SELECT p.primer_nombre  || ' ' || p.primer_apellido  AS est_nombre,
                       u.username                                     AS est_user,
                       p.correo                                       AS est_correo,
                       pa.primer_nombre || ' ' || pa.primer_apellido  AS ac_nombre,
                       ua.username                                    AS ac_user,
                       pa.correo                                      AS ac_correo,
                       (u.ultimo_login IS NULL)                       AS est_pendiente,
                       (ua.ultimo_login IS NULL)                      AS ac_pendiente
                FROM optimscul.estudiante e
                JOIN optimscul.persona p ON p.id = e.persona_id
                JOIN optimscul.usuario u ON u.persona_id = e.persona_id
                LEFT JOIN LATERAL (
                    SELECT ac.persona_id
                    FROM optimscul.estudiante_acudiente ea
                    JOIN optimscul.acudiente ac ON ac.id = ea.acudiente_id
                    WHERE ea.estudiante_id = e.id AND ea.es_principal = true
                    LIMIT 1
                ) best ON true
                LEFT JOIN optimscul.persona pa ON pa.id = best.persona_id
                LEFT JOIN optimscul.usuario ua ON ua.persona_id = best.persona_id
                WHERE e.id = :est
                """)
                .setParameter("est", estudianteId)
                .getResultList();

        if (filas.isEmpty())
            return Optional.empty();

        Object[] r = filas.get(0);
        return Optional.of(new DatosCredencialEstudiante(
                (String) r[0], (String) r[1], (String) r[2],
                (String) r[3], (String) r[4], (String) r[5],
                Boolean.TRUE.equals(r[6]), Boolean.TRUE.equals(r[7])));
    }
}
