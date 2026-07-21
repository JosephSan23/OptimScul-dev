package backend.academic.infrastructure.persistence;

import backend.academic.application.port.EstudianteDeClase;
import backend.academic.application.port.Horario.HorarioResumen;
import backend.academic.application.port.MiClaseResumen;
import backend.academic.infrastructure.persistence.entity.CargaAcademicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DocenteConsultaJpaRepository extends JpaRepository<CargaAcademicaEntity, UUID> {

    @Query(value = """
            SELECT ca.id                         AS "cargaId",
                   ca.grupo_id                   AS "grupoId",
                   ca.anio_lectivo_id            AS "anioLectivoId",
                   a.nombre                      AS "asignaturaNombre",
                   a.codigo                      AS "asignaturaCodigo",
                   g.nombre                      AS "grupoNombre",
                   g.codigo                      AS "grupoCodigo",
                   gr.nombre                     AS "gradoNombre",
                   ca.intensidad_horaria_semanal AS "intensidadHorariaSemanal",
                   (SELECT count(*) FROM optimscul.matricula m
                     WHERE m.grupo_id = ca.grupo_id AND m.estado = 'MATRICULADO') AS "totalEstudiantes"
            FROM optimscul.carga_academica ca
            JOIN optimscul.asignatura a ON a.id = ca.asignatura_id
            JOIN optimscul.grupo g      ON g.id = ca.grupo_id
            JOIN optimscul.grado gr     ON gr.id = g.grado_id
            WHERE ca.profesor_id     = CAST(:profesorId AS uuid)
              AND ca.anio_lectivo_id = CAST(:anioId AS uuid)
              AND ca.estado = 'ACTIVA'
            ORDER BY gr.orden, g.codigo, a.nombre
            """, nativeQuery = true)
    List<MiClaseResumen> misClases(@Param("profesorId") String profesorId, @Param("anioId") String anioId);

    @Query(value = """
            SELECT h.id                  AS "id",
                   h.carga_academica_id  AS "cargaAcademicaId",
                   h.dia_semana::text    AS "diaSemana",
                   to_char(h.hora_inicio, 'HH24:MI') AS "horaInicio",
                   to_char(h.hora_fin,   'HH24:MI') AS "horaFin",
                   h.aula                AS "aula",
                   h.activo              AS "activo",
                   a.nombre              AS "asignaturaNombre",
                   (p.primer_nombre || ' ' || p.primer_apellido) AS "profesorNombre",
                   s.nombre              AS "sedeNombre"
            FROM optimscul.horario_carga h
            JOIN optimscul.carga_academica ca ON ca.id = h.carga_academica_id
            JOIN optimscul.asignatura a       ON a.id  = ca.asignatura_id
            JOIN optimscul.profesor pr        ON pr.id = ca.profesor_id
            JOIN optimscul.persona p          ON p.id  = pr.persona_id
            LEFT JOIN optimscul.sede s        ON s.id  = h.sede_id
            WHERE ca.profesor_id     = CAST(:profesorId AS uuid)
              AND ca.anio_lectivo_id = CAST(:anioId AS uuid)
              AND ca.estado = 'ACTIVA'
              AND h.activo = true
            ORDER BY h.hora_inicio
            """, nativeQuery = true)
    List<HorarioResumen> miHorario(@Param("profesorId") String profesorId, @Param("anioId") String anioId);

    @Query(value = """
            SELECT e.id                 AS "estudianteId",
                   m.id                 AS "matriculaId",
                   e.codigo_estudiante  AS "codigoEstudiante",
                   (p.primer_nombre || ' ' || p.primer_apellido) AS "nombre",
                   p.numero_documento   AS "numeroDocumento"
            FROM optimscul.matricula m
            JOIN optimscul.estudiante e ON e.id = m.estudiante_id
            JOIN optimscul.persona p    ON p.id = e.persona_id
            WHERE m.grupo_id = CAST(:grupoId AS uuid)
              AND m.estado = 'MATRICULADO'
            ORDER BY p.primer_apellido, p.primer_nombre
            """, nativeQuery = true)
    List<EstudianteDeClase> estudiantesDeGrupo(@Param("grupoId") String grupoId);
}