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
        // Elige como destino el acudiente que TENGA correo; si ninguno tiene,
        // cae al acudiente principal. Así "todo junto" llega a un buzón real.
        List<Object[]> filas = em.createNativeQuery("""
                SELECT p.primer_nombre  || ' ' || p.primer_apellido  AS est_nombre,
                       u.username                                     AS est_user,
                       p.correo                                       AS est_correo,
                       pa.primer_nombre || ' ' || pa.primer_apellido  AS ac_nombre,
                       ua.username                                    AS ac_user,
                       pa.correo                                      AS ac_correo
                FROM optimscul.estudiante e
                JOIN optimscul.persona p ON p.id = e.persona_id
                JOIN optimscul.usuario u ON u.persona_id = e.persona_id
                LEFT JOIN LATERAL (
                    SELECT ac.persona_id
                    FROM optimscul.estudiante_acudiente ea
                    JOIN optimscul.acudiente ac ON ac.id = ea.acudiente_id
                    JOIN optimscul.persona pp ON pp.id = ac.persona_id
                    WHERE ea.estudiante_id = e.id
                    ORDER BY (pp.correo IS NOT NULL AND pp.correo <> '') DESC,
                             ea.es_principal DESC NULLS LAST
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
                (String) r[3], (String) r[4], (String) r[5]));
    }
}
