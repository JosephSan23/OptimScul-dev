package backend.staff.application.port;

import java.util.List;
import java.util.UUID;

public interface StaffConsultaRepository {
    List<StaffResumen> listarPorInstitucion(UUID institucionId);
}