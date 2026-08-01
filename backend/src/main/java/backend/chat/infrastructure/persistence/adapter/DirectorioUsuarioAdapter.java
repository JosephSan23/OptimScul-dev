package backend.chat.infrastructure.persistence.adapter;

import backend.chat.application.port.DirectorioUsuarioPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DirectorioUsuarioAdapter implements DirectorioUsuarioPort {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<UUID> institucionActiva(UUID usuarioId) {
        List<UUID> r = em.createNativeQuery("""
                SELECT ui.institucion_id
                FROM optimscul.usuario_institucion ui
                WHERE ui.usuario_id = :uid AND ui.activo = true
                ORDER BY ui.es_principal DESC
                """)
                .setParameter("uid", usuarioId)
                .setMaxResults(1)
                .getResultList();
        return r.isEmpty() ? Optional.empty() : Optional.of((UUID) r.get(0));
    }

    @Override
    public String nombreCompleto(UUID usuarioId) {
        List<?> r = em.createNativeQuery("""
                SELECT TRIM(CONCAT(p.primer_nombre, ' ', p.primer_apellido))
                FROM optimscul.usuario u
                JOIN optimscul.persona p ON p.id = u.persona_id
                WHERE u.id = :uid
                """)
                .setParameter("uid", usuarioId)
                .getResultList();
        return r.isEmpty() ? "Usuario" : (String) r.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Contacto> contactos(UUID usuarioId, UUID institucionId) {
        List<Object[]> filas = em.createNativeQuery("""
            SELECT sub.usuario_id, MAX(sub.nombre) AS nombre, MIN(sub.relacion) AS relacion
            FROM (
                -- (A) soy PROFESOR -> mis estudiantes
                SELECT u2.id AS usuario_id,
                    TRIM(CONCAT(p2.primer_nombre, ' ', p2.primer_apellido)) AS nombre,
                    'ESTUDIANTE' AS relacion
                FROM optimscul.usuario u1
                JOIN optimscul.profesor pr        ON pr.persona_id = u1.persona_id AND pr.institucion_id = :inst
                JOIN optimscul.carga_academica ca ON ca.profesor_id = pr.id AND ca.estado = 'ACTIVA'
                JOIN optimscul.matricula m        ON m.grupo_id = ca.grupo_id AND m.estado = 'MATRICULADO'
                JOIN optimscul.estudiante e       ON e.id = m.estudiante_id
                JOIN optimscul.usuario u2         ON u2.persona_id = e.persona_id
                JOIN optimscul.persona p2         ON p2.id = u2.persona_id
                WHERE u1.id = :uid

                UNION
                -- (B) soy PROFESOR -> acudientes de mis estudiantes
                SELECT u2.id,
                    TRIM(CONCAT(p2.primer_nombre, ' ', p2.primer_apellido)),
                    'ACUDIENTE'
                FROM optimscul.usuario u1
                JOIN optimscul.profesor pr        ON pr.persona_id = u1.persona_id AND pr.institucion_id = :inst
                JOIN optimscul.carga_academica ca ON ca.profesor_id = pr.id AND ca.estado = 'ACTIVA'
                JOIN optimscul.matricula m        ON m.grupo_id = ca.grupo_id AND m.estado = 'MATRICULADO'
                JOIN optimscul.estudiante_acudiente ea ON ea.estudiante_id = m.estudiante_id AND ea.autorizado_info_academica = true
                JOIN optimscul.acudiente ac       ON ac.id = ea.acudiente_id
                JOIN optimscul.usuario u2         ON u2.persona_id = ac.persona_id
                JOIN optimscul.persona p2         ON p2.id = u2.persona_id
                WHERE u1.id = :uid

                UNION
                -- (C) soy ESTUDIANTE -> mis profesores
                SELECT u2.id,
                    TRIM(CONCAT(p2.primer_nombre, ' ', p2.primer_apellido)),
                    'PROFESOR'
                FROM optimscul.usuario u1
                JOIN optimscul.estudiante e       ON e.persona_id = u1.persona_id AND e.institucion_id = :inst
                JOIN optimscul.matricula m        ON m.estudiante_id = e.id AND m.estado = 'MATRICULADO'
                JOIN optimscul.carga_academica ca ON ca.grupo_id = m.grupo_id AND ca.estado = 'ACTIVA'
                JOIN optimscul.profesor pr        ON pr.id = ca.profesor_id
                JOIN optimscul.usuario u2         ON u2.persona_id = pr.persona_id
                JOIN optimscul.persona p2         ON p2.id = u2.persona_id
                WHERE u1.id = :uid

                UNION
                -- (D) soy ACUDIENTE -> profesores de mis acudidos
                SELECT u2.id,
                    TRIM(CONCAT(p2.primer_nombre, ' ', p2.primer_apellido)),
                    'PROFESOR'
                FROM optimscul.usuario u1
                JOIN optimscul.acudiente ac       ON ac.persona_id = u1.persona_id AND ac.institucion_id = :inst
                JOIN optimscul.estudiante_acudiente ea ON ea.acudiente_id = ac.id AND ea.autorizado_info_academica = true
                JOIN optimscul.matricula m        ON m.estudiante_id = ea.estudiante_id AND m.estado = 'MATRICULADO'
                JOIN optimscul.carga_academica ca ON ca.grupo_id = m.grupo_id AND ca.estado = 'ACTIVA'
                JOIN optimscul.profesor pr        ON pr.id = ca.profesor_id
                JOIN optimscul.usuario u2         ON u2.persona_id = pr.persona_id
                JOIN optimscul.persona p2         ON p2.id = u2.persona_id
                WHERE u1.id = :uid
            ) sub
            WHERE sub.usuario_id <> :uid
            GROUP BY sub.usuario_id
            ORDER BY nombre
            """)
            .setParameter("uid", usuarioId)
            .setParameter("inst", institucionId)
            .getResultList();

        List<Contacto> out = new java.util.ArrayList<>();
        for (Object[] f : filas) {
            out.add(new Contacto((UUID) f[0], (String) f[1], (String) f[2]));
        }
        return out;
    }

    @Override
    public boolean puedenChatear(UUID usuarioA, UUID usuarioB, UUID institucionId) {
        return contactos(usuarioA, institucionId).stream()
                .anyMatch(c -> c.usuarioId().equals(usuarioB));
    }
}