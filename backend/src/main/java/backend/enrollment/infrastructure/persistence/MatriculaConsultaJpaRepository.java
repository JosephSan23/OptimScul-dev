package backend.enrollment.infrastructure.persistence;

import backend.enrollment.application.port.MatriculaResumen;
import backend.enrollment.infrastructure.persistence.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MatriculaConsultaJpaRepository extends JpaRepository<MatriculaEntity, UUID> {

    @Query(value = """
            SELECT m.id                 AS "id",
                   m.estudiante_id      AS "estudianteId",
                   m.grupo_id           AS "grupoId",
                   m.codigo_matricula   AS "codigoMatricula",
                   (p.primer_nombre || ' ' || p.primer_apellido) AS "estudianteNombre",
                   e.codigo_estudiante  AS "codigoEstudiante",
                   p.numero_documento   AS "numeroDocumento",
                   g.nombre             AS "grupoNombre",
                   gr.nombre            AS "gradoNombre",
                   m.tipo::text         AS "tipo",
                   m.estado::text       AS "estado",
                   to_char(m.fecha_matricula, 'YYYY-MM-DD') AS "fechaMatricula"
            FROM optimscul.matricula m
            JOIN optimscul.estudiante e ON e.id = m.estudiante_id
            JOIN optimscul.persona p    ON p.id = e.persona_id
            LEFT JOIN optimscul.grupo g ON g.id = m.grupo_id
            LEFT JOIN optimscul.grado gr ON gr.id = g.grado_id
            WHERE m.institucion_id  = CAST(:institucionId AS uuid)
              AND m.anio_lectivo_id = CAST(:anioId AS uuid)
            ORDER BY p.primer_apellido, p.primer_nombre
            """, nativeQuery = true)
    List<MatriculaResumen> listarPorAnio(@Param("institucionId") String institucionId, @Param("anioId") String anioId);
}