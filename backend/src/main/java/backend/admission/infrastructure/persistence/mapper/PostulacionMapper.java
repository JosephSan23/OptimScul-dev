package backend.admission.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.admission.domain.model.Postulacion;
import backend.admission.infrastructure.persistence.entity.PostulacionEntity;

@Component
public class PostulacionMapper {

    public Postulacion toDomain(PostulacionEntity entity) {
        if (entity == null) return null;
        Postulacion domain = new Postulacion();
        domain.setId(entity.getId());
        domain.setInstitucionId(entity.getInstitucionId());
        domain.setAnioLectivoId(entity.getAnioLectivoId());
        domain.setSedeId(entity.getSedeId());
        domain.setJornadaId(entity.getJornadaId());
        domain.setGradoAspiraId(entity.getGradoAspiraId());
        domain.setCodigo(entity.getCodigo());
        domain.setFechaPostulacion(entity.getFechaPostulacion());
        domain.setCanal(entity.getCanal());
        domain.setEstado(entity.getEstado());
        domain.setObservaciones(entity.getObservaciones());
        domain.setObservacionesInternas(entity.getObservacionesInternas());
        domain.setCupoReservado(entity.getCupoReservado());
        domain.setAprobadaPorUsuarioId(entity.getAprobadaPorUsuarioId());
        domain.setFechaAprobacion(entity.getFechaAprobacion());
        domain.setRechazadaPorUsuarioId(entity.getRechazadaPorUsuarioId());
        domain.setFechaRechazo(entity.getFechaRechazo());
        domain.setMotivoRechazo(entity.getMotivoRechazo());
        domain.setConvertidaEnEstudianteId(entity.getConvertidaEnEstudianteId());
        domain.setConvertidaEnMatriculaId(entity.getConvertidaEnMatriculaId());
        domain.setFechaConversion(entity.getFechaConversion());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public PostulacionEntity toEntity(Postulacion domain) {
        if (domain == null) return null;
        PostulacionEntity entity = new PostulacionEntity();
        entity.setId(domain.getId());
        entity.setInstitucionId(domain.getInstitucionId());
        entity.setAnioLectivoId(domain.getAnioLectivoId());
        entity.setSedeId(domain.getSedeId());
        entity.setJornadaId(domain.getJornadaId());
        entity.setGradoAspiraId(domain.getGradoAspiraId());
        entity.setCodigo(domain.getCodigo());
        entity.setFechaPostulacion(domain.getFechaPostulacion());
        entity.setCanal(domain.getCanal());
        entity.setEstado(domain.getEstado());
        entity.setObservaciones(domain.getObservaciones());
        entity.setObservacionesInternas(domain.getObservacionesInternas());
        entity.setCupoReservado(domain.getCupoReservado());
        entity.setAprobadaPorUsuarioId(domain.getAprobadaPorUsuarioId());
        entity.setFechaAprobacion(domain.getFechaAprobacion());
        entity.setRechazadaPorUsuarioId(domain.getRechazadaPorUsuarioId());
        entity.setFechaRechazo(domain.getFechaRechazo());
        entity.setMotivoRechazo(domain.getMotivoRechazo());
        entity.setConvertidaEnEstudianteId(domain.getConvertidaEnEstudianteId());
        entity.setConvertidaEnMatriculaId(domain.getConvertidaEnMatriculaId());
        entity.setFechaConversion(domain.getFechaConversion());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
