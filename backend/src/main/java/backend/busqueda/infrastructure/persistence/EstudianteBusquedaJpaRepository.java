package backend.busqueda.infrastructure.persistence;

import backend.busqueda.application.EstudianteBusquedaResumen;
import backend.people.infrastructure.persistence.entity.EstudianteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EstudianteBusquedaJpaRepository extends JpaRepository<EstudianteEntity, UUID> {

    @Query(value = """
            SELECT e.id                  AS "id",
                   (p.primer_nombre || ' ' || p.primer_apellido) AS "nombre",
                   e.codigo_estudiante   AS "codigoEstudiante",
                   p.numero_documento    AS "numeroDocumento"
            FROM optimscul.estudiante e
            JOIN optimscul.persona p ON p.id = e.persona_id
            WHERE e.institucion_id = CAST(:institucionId AS uuid)
              AND (p.primer_nombre ILIKE :q
                   OR p.primer_apellido ILIKE :q
                   OR (p.primer_nombre || ' ' || p.primer_apellido) ILIKE :q
                   OR e.codigo_estudiante ILIKE :q
                   OR p.numero_documento ILIKE :q)
            ORDER BY p.primer_apellido, p.primer_nombre
            """, nativeQuery = true)
    List<EstudianteBusquedaResumen> buscar(@Param("institucionId") String institucionId,
                                           @Param("q") String q,
                                           Pageable limite);
}
