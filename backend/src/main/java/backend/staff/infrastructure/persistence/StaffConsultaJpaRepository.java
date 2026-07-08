package backend.staff.infrastructure.persistence;

import backend.staff.application.port.StaffResumen;
import backend.security.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StaffConsultaJpaRepository extends JpaRepository<UsuarioEntity, UUID> {

    @Query(value = """
            SELECT u.id            AS "usuarioId",
                   u.username      AS "username",
                   u.email_login   AS "emailLogin",
                   u.estado::text  AS "estado",
                   u.ultimo_login  AS "ultimoLogin",
                   u.created_at    AS "createdAt",
                   p.primer_nombre     AS "primerNombre",
                   p.primer_apellido   AS "primerApellido",
                   p.numero_documento  AS "numeroDocumento",
                   p.correo            AS "correo",
                   r.codigo AS "rolCodigo",
                   r.nombre AS "rolNombre"
            FROM optimscul.usuario u
            JOIN optimscul.persona p     ON p.id = u.persona_id
            JOIN optimscul.usuario_rol ur ON ur.usuario_id = u.id AND ur.activo = true
            JOIN optimscul.rol r          ON r.id = ur.rol_id
            WHERE ur.institucion_id = CAST(:institucionId AS uuid)
              AND r.codigo IN ('RECTOR','COORDINADOR_ACADEMICO','SECRETARIA','DOCENTE','TESORERIA')
            ORDER BY u.created_at DESC
            """, nativeQuery = true)
    List<StaffResumen> listarPorInstitucion(@Param("institucionId") String institucionId);
}