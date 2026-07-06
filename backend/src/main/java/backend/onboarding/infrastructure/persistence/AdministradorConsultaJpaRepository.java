package backend.onboarding.infrastructure.persistence;

import backend.onboarding.application.port.AdministradorResumen;
import backend.security.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface AdministradorConsultaJpaRepository extends JpaRepository<UsuarioEntity, UUID> {

    @Query(value = """
            SELECT u.id                       AS "usuarioId",
                   u.username                 AS "username",
                   u.estado::text             AS "estado",
                   u.requiere_cambio_password AS "requiereCambioPassword",
                   u.ultimo_login             AS "ultimoLogin",
                   u.email_login              AS "emailLogin",
                   p.tipo_documento::text     AS "tipoDocumento",
                   u.created_at               AS "createdAt",
                   p.primer_nombre            AS "primerNombre",
                   p.primer_apellido          AS "primerApellido",
                   p.numero_documento         AS "numeroDocumento",
                   p.correo                   AS "correo",
                   i.id                       AS "institucionId",
                   i.nombre                   AS "institucionNombre"
            FROM optimscul.usuario u
            JOIN optimscul.persona p            ON p.id = u.persona_id
            JOIN optimscul.usuario_rol ur       ON ur.usuario_id = u.id AND ur.activo = true
            JOIN optimscul.rol r                ON r.id = ur.rol_id AND r.codigo = 'ADMIN_INSTITUCION'
            LEFT JOIN optimscul.usuario_institucion uinst
                   ON uinst.usuario_id = u.id AND uinst.es_principal = true
            LEFT JOIN optimscul.institucion i   ON i.id = uinst.institucion_id
            ORDER BY u.created_at DESC
            """, nativeQuery = true)
    List<AdministradorResumen> listarAdministradores();
}