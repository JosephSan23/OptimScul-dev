package backend.profile.application.port;

import java.util.Optional;
import java.util.UUID;

public interface ProfesorPerfilRepository {
    record Extras(String especialidad, String tituloProfesional) {
    }

    Optional<Extras> leer(UUID personaId); // present() == es docente

    void actualizar(UUID personaId, String especialidad, String tituloProfesional);
}