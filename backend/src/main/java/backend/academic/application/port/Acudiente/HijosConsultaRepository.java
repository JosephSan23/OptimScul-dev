package backend.academic.application.port.Acudiente;

import java.util.List;
import java.util.UUID;

public interface HijosConsultaRepository {
    List<HijoResumen> hijosDeAcudiente(UUID acudienteId);
}