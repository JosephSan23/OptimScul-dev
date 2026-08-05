package backend.profile.infrastructure.persistence;

import backend.profile.application.port.ProfesorPerfilRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProfesorPerfilAdapter implements ProfesorPerfilRepository {

    @PersistenceContext private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Extras> leer(UUID personaId) {
        List<Object[]> r = em.createNativeQuery("""
                SELECT especialidad, titulo_profesional
                FROM optimscul.profesor WHERE persona_id = :pid
                """).setParameter("pid", personaId).getResultList();
        if (r.isEmpty()) return Optional.empty();
        Object[] row = r.get(0);
        return Optional.of(new Extras((String) row[0], (String) row[1]));
    }

    @Override
    @Transactional
    public void actualizar(UUID personaId, String especialidad, String tituloProfesional) {
        em.createNativeQuery("""
                UPDATE optimscul.profesor
                SET especialidad = :esp, titulo_profesional = :tit, updated_at = now()
                WHERE persona_id = :pid
                """)
                .setParameter("esp", especialidad)
                .setParameter("tit", tituloProfesional)
                .setParameter("pid", personaId)
                .executeUpdate();
    }
}