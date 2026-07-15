package backend.security.infrastructure.persistence;

import backend.security.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, UUID> {

    @Query("SELECT u FROM UsuarioEntity u WHERE u.username = :valor OR u.emailLogin = :valor")
    Optional<UsuarioEntity> findByUsernameOrCorreo(@Param("valor") String valor);

    // Trae los códigos de rol activos del usuario en cualquier institución
    @Query(value = """
            SELECT r.codigo
            FROM optimscul.usuario_rol ur
            JOIN optimscul.rol r ON r.id = ur.rol_id
            WHERE ur.usuario_id = CAST(:usuarioId AS uuid)
              AND ur.activo = true
              AND r.activo = true
            """, nativeQuery = true)
    List<String> findRolesByUsuarioId(@Param("usuarioId") String usuarioId);

    @Query(value = """
            SELECT r.codigo
            FROM optimscul.usuario_rol ur
            JOIN optimscul.rol r ON r.id = ur.rol_id
            WHERE ur.usuario_id = CAST(:usuarioId AS uuid)
              AND ur.institucion_id = CAST(:institucionId AS uuid)
              AND ur.activo = true
              AND r.activo = true
            """, nativeQuery = true)
    List<String> findRolesByUsuarioIdAndInstitucion(@Param("usuarioId") String usuarioId,
                                                     @Param("institucionId") String institucionId);


  Optional<UsuarioEntity> findByPersonaId(UUID personaId);
}