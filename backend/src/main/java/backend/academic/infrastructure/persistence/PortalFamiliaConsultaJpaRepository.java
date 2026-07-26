package backend.academic.infrastructure.persistence;

import backend.academic.application.port.Asistencia.AsistenciaMateriaFila;
import backend.academic.application.port.Horario.HorarioResumen;
import backend.academic.infrastructure.persistence.entity.HorarioCargaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PortalFamiliaConsultaJpaRepository extends JpaRepository<HorarioCargaEntity, UUID> {

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
            WHERE ca.grupo_id = CAST(:grupoId AS uuid)
              AND ca.anio_lectivo_id = CAST(:anioId AS uuid)
              AND ca.estado = 'ACTIVA' AND h.activo = true
            ORDER BY h.hora_inicio
            """, nativeQuery = true)
    List<HorarioResumen> horarioDeGrupo(@Param("grupoId") String grupoId, @Param("anioId") String anioId);

    @Query(value = """
            SELECT a.nombre AS "asignaturaNombre",
                   count(*) FILTER (WHERE ac.tipo_asistencia = 'PRESENTE')    AS "presente",
                   count(*) FILTER (WHERE ac.tipo_asistencia = 'AUSENTE')     AS "ausente",
                   count(*) FILTER (WHERE ac.tipo_asistencia = 'TARDE')       AS "tarde",
                   count(*) FILTER (WHERE ac.tipo_asistencia = 'JUSTIFICADA') AS "justificada",
                   count(ac.id) AS "total"
            FROM optimscul.asistencia_clase ac
            JOIN optimscul.sesion_clase se  ON se.id = ac.sesion_clase_id AND se.estado = 'DICTADA'
            JOIN optimscul.carga_academica ca ON ca.id = se.carga_academica_id
            JOIN optimscul.asignatura a     ON a.id = ca.asignatura_id
            WHERE ac.estudiante_id = CAST(:estudianteId AS uuid)
              AND ca.anio_lectivo_id = CAST(:anioId AS uuid)
            GROUP BY a.nombre
            ORDER BY a.nombre
            """, nativeQuery = true)
    List<AsistenciaMateriaFila> asistenciaPorMateria(@Param("estudianteId") String estudianteId, @Param("anioId") String anioId);
}