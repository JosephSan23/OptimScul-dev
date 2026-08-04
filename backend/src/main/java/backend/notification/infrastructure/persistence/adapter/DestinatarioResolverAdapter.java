package backend.notification.infrastructure.persistence.adapter;

import backend.notification.application.port.DestinatarioResolverPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DestinatarioResolverAdapter implements DestinatarioResolverPort {

    @PersistenceContext private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<UUID> usuariosDeEstudianteYAcudientes(UUID estudianteId) {
        return em.createNativeQuery("""
            SELECT u.id FROM optimscul.estudiante e
            JOIN optimscul.usuario u ON u.persona_id = e.persona_id
            WHERE e.id = :est
            UNION
            SELECT u.id FROM optimscul.estudiante_acudiente ea
            JOIN optimscul.acudiente ac ON ac.id = ea.acudiente_id AND ea.autorizado_info_academica = true
            JOIN optimscul.usuario u ON u.persona_id = ac.persona_id
            WHERE ea.estudiante_id = :est
            """).setParameter("est", estudianteId).getResultList();
    }

    @Override
    public Optional<String> emailDe(UUID usuarioId) {
        List<?> r = em.createNativeQuery("""
            SELECT COALESCE(u.email_login, p.correo)
            FROM optimscul.usuario u JOIN optimscul.persona p ON p.id = u.persona_id
            WHERE u.id = :uid
            """).setParameter("uid", usuarioId).getResultList();
        return r.isEmpty() || r.get(0) == null ? Optional.empty() : Optional.of((String) r.get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UUID> usuariosDeGrupo(UUID grupoId) {
        return em.createNativeQuery("""
            SELECT u.id
            FROM optimscul.matricula m
            JOIN optimscul.estudiante e ON e.id = m.estudiante_id
            JOIN optimscul.usuario u ON u.persona_id = e.persona_id
            WHERE m.grupo_id = :grupo AND m.estado = 'MATRICULADO'
            UNION
            SELECT u.id
            FROM optimscul.matricula m
            JOIN optimscul.estudiante_acudiente ea ON ea.estudiante_id = m.estudiante_id AND ea.autorizado_info_academica = true
            JOIN optimscul.acudiente ac ON ac.id = ea.acudiente_id
            JOIN optimscul.usuario u ON u.persona_id = ac.persona_id
            WHERE m.grupo_id = :grupo AND m.estado = 'MATRICULADO'
            """).setParameter("grupo", grupoId).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UUID> usuariosDeInstitucionAnio(UUID institucionId, UUID anioLectivoId) {
        return em.createNativeQuery("""
            SELECT u.id
            FROM optimscul.matricula m
            JOIN optimscul.estudiante e ON e.id = m.estudiante_id
            JOIN optimscul.usuario u ON u.persona_id = e.persona_id
            WHERE m.institucion_id = :inst AND m.anio_lectivo_id = :anio AND m.estado = 'MATRICULADO'
            UNION
            SELECT u.id
            FROM optimscul.matricula m
            JOIN optimscul.estudiante_acudiente ea ON ea.estudiante_id = m.estudiante_id AND ea.autorizado_info_academica = true
            JOIN optimscul.acudiente ac ON ac.id = ea.acudiente_id
            JOIN optimscul.usuario u ON u.persona_id = ac.persona_id
            WHERE m.institucion_id = :inst AND m.anio_lectivo_id = :anio AND m.estado = 'MATRICULADO'
            """).setParameter("inst", institucionId).setParameter("anio", anioLectivoId).getResultList();
    }
}