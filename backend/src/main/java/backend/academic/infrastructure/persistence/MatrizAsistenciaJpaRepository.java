package backend.academic.infrastructure.persistence;

import backend.academic.application.port.MarcaAsistenciaFila;
import backend.academic.application.port.SesionFechaFila;
import backend.academic.infrastructure.persistence.entity.SesionClaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MatrizAsistenciaJpaRepository extends JpaRepository<SesionClaseEntity, UUID> {

    @Query(value = """
            SELECT s.id AS "sesionId",
                   to_char(s.fecha, 'DD/MM/YYYY') AS "fecha"
            FROM optimscul.sesion_clase s
            WHERE s.carga_academica_id = CAST(:cargaId AS uuid)
              AND s.estado = 'DICTADA'
            ORDER BY s.fecha
            """, nativeQuery = true)
    List<SesionFechaFila> sesionesDeCarga(@Param("cargaId") String cargaId);

    @Query(value = """
            SELECT ac.estudiante_id     AS "estudianteId",
                   ac.sesion_clase_id   AS "sesionId",
                   ac.tipo_asistencia::text AS "tipo"
            FROM optimscul.asistencia_clase ac
            JOIN optimscul.sesion_clase s ON s.id = ac.sesion_clase_id
            WHERE s.carga_academica_id = CAST(:cargaId AS uuid)
              AND s.estado = 'DICTADA'
            """, nativeQuery = true)
    List<MarcaAsistenciaFila> marcasDeCarga(@Param("cargaId") String cargaId);
}