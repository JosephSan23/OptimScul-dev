package backend.academic.application.service;

import backend.academic.application.port.Asistencia.AsistenciaMateriaFila;
import backend.academic.application.port.Asistencia.PortalFamiliaConsultaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AsistenciaFamiliaService {
    private final PortalFamiliaConsultaRepository portalRepo;

    public AsistenciaFamiliaService(PortalFamiliaConsultaRepository portalRepo) { this.portalRepo = portalRepo; }

    public List<AsistenciaMateriaFila> calcular(UUID estudianteId, UUID anioId) {
        return portalRepo.asistenciaPorMateria(estudianteId, anioId);
    }
}