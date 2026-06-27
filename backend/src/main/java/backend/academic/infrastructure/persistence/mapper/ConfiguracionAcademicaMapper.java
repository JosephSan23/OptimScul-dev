package backend.academic.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.academic.domain.model.ConfiguracionAcademica;
import backend.academic.infrastructure.persistence.entity.ConfiguracionAcademicaEntity;

@Component
public class ConfiguracionAcademicaMapper {

    public ConfiguracionAcademica toDomain(ConfiguracionAcademicaEntity entity) {
        if (entity == null) return null;
        ConfiguracionAcademica domain = new ConfiguracionAcademica();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setUsaPeriodos(entity.getUsaPeriodos());
        domain.setNumeroPeriodos(entity.getNumeroPeriodos());
        domain.setNotaMinimaAprobacion(entity.getNotaMinimaAprobacion());
        domain.setNotaMinima(entity.getNotaMinima());
        domain.setNotaMaxima(entity.getNotaMaxima());
        domain.setDecimalesNota(entity.getDecimalesNota());
        domain.setUsaRecuperacion(entity.getUsaRecuperacion());
        domain.setAsistenciaPorClase(entity.getAsistenciaPorClase());
        domain.setManejaComportamiento(entity.getManejaComportamiento());
        domain.setManejaPuestos(entity.getManejaPuestos());
        domain.setPorcentajeInasistenciaReprobacion(entity.getPorcentajeInasistenciaReprobacion());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public ConfiguracionAcademicaEntity toEntity(ConfiguracionAcademica domain) {
        if (domain == null) return null;
        ConfiguracionAcademicaEntity entity = new ConfiguracionAcademicaEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setUsaPeriodos(domain.getUsaPeriodos());
        entity.setNumeroPeriodos(domain.getNumeroPeriodos());
        entity.setNotaMinimaAprobacion(domain.getNotaMinimaAprobacion());
        entity.setNotaMinima(domain.getNotaMinima());
        entity.setNotaMaxima(domain.getNotaMaxima());
        entity.setDecimalesNota(domain.getDecimalesNota());
        entity.setUsaRecuperacion(domain.getUsaRecuperacion());
        entity.setAsistenciaPorClase(domain.getAsistenciaPorClase());
        entity.setManejaComportamiento(domain.getManejaComportamiento());
        entity.setManejaPuestos(domain.getManejaPuestos());
        entity.setPorcentajeInasistenciaReprobacion(domain.getPorcentajeInasistenciaReprobacion());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
