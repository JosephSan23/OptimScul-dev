package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.ActividadAcademicaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MisActividadesJpaRepository extends Repository<ActividadAcademicaEntity, UUID> {

    @Query(value = """
            SELECT a.id            AS "actividadId",
                   a.titulo        AS "titulo",
                   a.tipo::text    AS "tipo",
                   a.fecha_entrega AS "fechaEntrega",
                   a.fecha_cierre  AS "fechaCierre",
                   a.nota_maxima   AS "notaMaxima",
                   asg.nombre      AS "asignatura",
                   ca.id           AS "cargaId",
                   ea.estado::text AS "estadoEntrega",
                   cal.nota_obtenida AS "notaObtenida"
            FROM optimscul.matricula m
            JOIN optimscul.carga_academica ca
                 ON ca.grupo_id = m.grupo_id
                 AND ca.anio_lectivo_id = m.anio_lectivo_id
                 AND ca.estado = 'ACTIVA'
            JOIN optimscul.asignatura asg
                 ON asg.id = ca.asignatura_id
            JOIN optimscul.actividad_academica a
                 ON a.carga_academica_id = ca.id
                 AND a.estado = 'PUBLICADA'
            LEFT JOIN optimscul.entrega_actividad ea
                 ON ea.actividad_id = a.id
                 AND ea.estudiante_id = m.estudiante_id
            LEFT JOIN optimscul.calificacion_actividad cal
                 ON cal.actividad_id = a.id
                 AND cal.estudiante_id = m.estudiante_id
                 AND cal.anulada = false
                 AND cal.es_recuperacion = false
            WHERE m.estudiante_id = :estudianteId
              AND m.anio_lectivo_id = :anioId
              AND m.grupo_id IS NOT NULL
            ORDER BY asg.nombre, a.fecha_entrega
            """, nativeQuery = true)
    List<MiActividadRow> misActividades(@Param("estudianteId") UUID estudianteId,
                                        @Param("anioId") UUID anioId);
}