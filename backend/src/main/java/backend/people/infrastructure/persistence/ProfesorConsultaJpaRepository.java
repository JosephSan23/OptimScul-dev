package backend.people.infrastructure.persistence;

import backend.people.application.port.profesor.ProfesorResumen;
import backend.people.infrastructure.persistence.entity.ProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProfesorConsultaJpaRepository extends JpaRepository<ProfesorEntity, UUID> {

    @Query(value = """
            SELECT pr.id                AS "id",
                   (p.primer_nombre || ' ' || p.primer_apellido) AS "nombre",
                   pr.codigo_profesor   AS "codigoProfesor",
                   pr.especialidad      AS "especialidad",
                   pr.estado::text      AS "estado"
            FROM optimscul.profesor pr
            JOIN optimscul.persona p ON p.id = pr.persona_id
            WHERE pr.institucion_id = CAST(:institucionId AS uuid)
            ORDER BY p.primer_apellido, p.primer_nombre
            """, nativeQuery = true)
    List<ProfesorResumen> listarPorInstitucion(@Param("institucionId") String institucionId);
}