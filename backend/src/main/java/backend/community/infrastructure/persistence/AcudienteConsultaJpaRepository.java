package backend.community.infrastructure.persistence;

import backend.community.application.port.AcudienteDeEstudiante;
import backend.people.infrastructure.persistence.entity.EstudianteAcudienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AcudienteConsultaJpaRepository extends JpaRepository<EstudianteAcudienteEntity, UUID> {

    @Query(value = """
            SELECT ea.id                        AS "vinculoId",
                   a.id                         AS "acudienteId",
                   ea.parentesco::text          AS "parentesco",
                   ea.es_principal              AS "esPrincipal",
                   ea.autorizado_recogida       AS "autorizadoRecogida",
                   ea.autorizado_info_academica AS "autorizadoInfoAcademica",
                   p.primer_nombre              AS "primerNombre",
                   p.primer_apellido            AS "primerApellido",
                   p.numero_documento           AS "numeroDocumento",
                   p.correo                     AS "correo",
                   u.username                   AS "username",
                   a.ocupacion                  AS "ocupacion",
                   a.empresa                    AS "empresa",
                   a.estado::text               AS "estado"
            FROM optimscul.estudiante_acudiente ea
            JOIN optimscul.acudiente a      ON a.id = ea.acudiente_id
            JOIN optimscul.persona p        ON p.id = a.persona_id
            LEFT JOIN optimscul.usuario u   ON u.persona_id = a.persona_id
            WHERE ea.estudiante_id = CAST(:estudianteId AS uuid)
            ORDER BY ea.es_principal DESC, p.primer_apellido
            """, nativeQuery = true)
    List<AcudienteDeEstudiante> listarPorEstudiante(@Param("estudianteId") String estudianteId);
}