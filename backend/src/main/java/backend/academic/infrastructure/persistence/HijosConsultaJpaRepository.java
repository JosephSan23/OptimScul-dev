package backend.academic.infrastructure.persistence;

import backend.academic.application.port.Acudiente.HijoResumen;
import backend.people.infrastructure.persistence.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface HijosConsultaJpaRepository extends JpaRepository<EstudianteEntity, UUID> {

    @Query(value = """
            SELECT e.id                AS "estudianteId",
                   (p.primer_nombre || ' ' || p.primer_apellido) AS "nombre",
                   e.codigo_estudiante AS "codigoEstudiante",
                   p.numero_documento  AS "numeroDocumento"
            FROM optimscul.estudiante_acudiente ea
            JOIN optimscul.estudiante e ON e.id = ea.estudiante_id
            JOIN optimscul.persona p    ON p.id = e.persona_id
            WHERE ea.acudiente_id = CAST(:acudienteId AS uuid)
            ORDER BY p.primer_nombre, p.primer_apellido
            """, nativeQuery = true)
    List<HijoResumen> hijosDeAcudiente(@Param("acudienteId") String acudienteId);
}