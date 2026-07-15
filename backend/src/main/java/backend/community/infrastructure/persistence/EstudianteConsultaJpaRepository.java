package backend.community.infrastructure.persistence;

import backend.community.application.port.EstudianteResumen;
import backend.people.infrastructure.persistence.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EstudianteConsultaJpaRepository extends JpaRepository<EstudianteEntity, UUID> {

    @Query(value = """
            SELECT e.id                AS "estudianteId",
                   e.codigo_estudiante AS "codigoEstudiante",
                   e.estado::text          AS "estado",
                   e.fecha_ingreso     AS "fechaIngreso",
                   u.id                AS "usuarioId",
                   u.username          AS "username",
                   p.primer_nombre     AS "primerNombre",
                   p.primer_apellido   AS "primerApellido",
                   p.numero_documento  AS "numeroDocumento",
                   p.correo            AS "correo"
            FROM optimscul.estudiante e
            JOIN optimscul.persona p       ON p.id = e.persona_id
            LEFT JOIN optimscul.usuario u  ON u.persona_id = e.persona_id
            WHERE e.institucion_id = CAST(:institucionId AS uuid)
            ORDER BY e.created_at DESC
            """, nativeQuery = true)
    List<EstudianteResumen> listarPorInstitucion(@Param("institucionId") String institucionId);
}
