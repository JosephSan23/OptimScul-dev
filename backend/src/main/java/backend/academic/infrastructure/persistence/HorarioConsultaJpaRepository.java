package backend.academic.infrastructure.persistence;

import backend.academic.application.port.Horario.HorarioResumen;
import backend.academic.infrastructure.persistence.entity.HorarioCargaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface HorarioConsultaJpaRepository extends JpaRepository<HorarioCargaEntity, UUID> {

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
            WHERE h.institucion_id = CAST(:institucionId AS uuid)
              AND ca.grupo_id      = CAST(:grupoId AS uuid)
            ORDER BY h.hora_inicio
            """, nativeQuery = true)
    List<HorarioResumen> listarPorGrupo(@Param("institucionId") String institucionId,
                                        @Param("grupoId") String grupoId);

    @Query(value = """
            SELECT count(*) FROM optimscul.horario_carga h
            JOIN optimscul.carga_academica ca ON ca.id = h.carga_academica_id
            WHERE ca.profesor_id     = CAST(:profesorId AS uuid)
              AND ca.anio_lectivo_id = CAST(:anioId AS uuid)
              AND ca.estado = 'ACTIVA'
              AND h.activo = true
              AND h.dia_semana = CAST(:dia AS optimscul.dia_semana_enum)
              AND h.hora_inicio < CAST(:horaFin AS time)
              AND h.hora_fin    > CAST(:horaInicio AS time)
              AND h.id <> CAST(:excluirId AS uuid)
            """, nativeQuery = true)
    long contarChoquesProfesor(@Param("profesorId") String profesorId, @Param("anioId") String anioId,
                               @Param("dia") String dia, @Param("horaInicio") String horaInicio,
                               @Param("horaFin") String horaFin, @Param("excluirId") String excluirId);

    @Query(value = """
            SELECT count(*) FROM optimscul.horario_carga h
            JOIN optimscul.carga_academica ca ON ca.id = h.carga_academica_id
            WHERE ca.grupo_id        = CAST(:grupoId AS uuid)
              AND ca.anio_lectivo_id = CAST(:anioId AS uuid)
              AND ca.estado = 'ACTIVA'
              AND h.activo = true
              AND h.dia_semana = CAST(:dia AS optimscul.dia_semana_enum)
              AND h.hora_inicio < CAST(:horaFin AS time)
              AND h.hora_fin    > CAST(:horaInicio AS time)
              AND h.id <> CAST(:excluirId AS uuid)
            """, nativeQuery = true)
    long contarChoquesGrupo(@Param("grupoId") String grupoId, @Param("anioId") String anioId,
                            @Param("dia") String dia, @Param("horaInicio") String horaInicio,
                            @Param("horaFin") String horaFin, @Param("excluirId") String excluirId);
}