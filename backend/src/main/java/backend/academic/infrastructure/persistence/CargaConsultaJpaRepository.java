package backend.academic.infrastructure.persistence;

import backend.academic.application.port.CargaAcademica.CargaResumen;
import backend.academic.infrastructure.persistence.entity.CargaAcademicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CargaConsultaJpaRepository extends JpaRepository<CargaAcademicaEntity, UUID> {

    @Query(value = """
            SELECT ca.id                          AS "id",
                   ca.profesor_id                 AS "profesorId",
                   ca.grupo_id                    AS "grupoId",
                   ca.asignatura_id               AS "asignaturaId",
                   (p.primer_nombre || ' ' || p.primer_apellido) AS "profesorNombre",
                   pr.codigo_profesor             AS "codigoProfesor",
                   a.nombre                       AS "asignaturaNombre",
                   g.nombre                       AS "grupoNombre",
                   g.codigo                       AS "grupoCodigo",
                   gr.nombre                      AS "gradoNombre",
                   ca.intensidad_horaria_semanal  AS "intensidadHorariaSemanal",
                   ca.estado::text                AS "estado",
                   ca.observaciones               AS "observaciones"
            FROM optimscul.carga_academica ca
            JOIN optimscul.profesor pr   ON pr.id = ca.profesor_id
            JOIN optimscul.persona p     ON p.id  = pr.persona_id
            JOIN optimscul.asignatura a  ON a.id  = ca.asignatura_id
            JOIN optimscul.grupo g       ON g.id  = ca.grupo_id
            JOIN optimscul.grado gr      ON gr.id = g.grado_id
            WHERE ca.institucion_id  = CAST(:institucionId AS uuid)
              AND ca.anio_lectivo_id = CAST(:anioId AS uuid)
            ORDER BY gr.orden, g.codigo, a.nombre
            """, nativeQuery = true)
    List<CargaResumen> listarPorAnio(@Param("institucionId") String institucionId, @Param("anioId") String anioId);
}