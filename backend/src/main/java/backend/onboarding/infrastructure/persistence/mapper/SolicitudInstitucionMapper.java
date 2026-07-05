package backend.onboarding.infrastructure.persistence.mapper;

import backend.onboarding.domain.model.SolicitudInstitucion;
import backend.onboarding.infrastructure.persistence.entity.SolicitudInstitucionEntity;
import org.springframework.stereotype.Component;

@Component
public class SolicitudInstitucionMapper {

    public SolicitudInstitucion toDomain(SolicitudInstitucionEntity e) {
        SolicitudInstitucion s = new SolicitudInstitucion();
        s.setId(e.getId());
        s.setNombreColegio(e.getNombreColegio());
        s.setNit(e.getNit());
        s.setCiudad(e.getCiudad());
        s.setDireccion(e.getDireccion());
        s.setTelefono(e.getTelefono());
        s.setNombreContacto(e.getNombreContacto());
        s.setCorreo(e.getCorreo());
        s.setMensaje(e.getMensaje());
        s.setEnviadaPorUsuarioId(e.getEnviadaPorUsuarioId());
        s.setEstado(e.getEstado());
        s.setRevisadaPorUsuarioId(e.getRevisadaPorUsuarioId());
        s.setFechaRevision(e.getFechaRevision());
        s.setMotivoRechazo(e.getMotivoRechazo());
        s.setConvertidaEnInstitucionId(e.getConvertidaEnInstitucionId());
        s.setFechaConversion(e.getFechaConversion());
        s.setCreatedAt(e.getCreatedAt());
        s.setUpdatedAt(e.getUpdatedAt());
        return s;
    }

    public SolicitudInstitucionEntity toEntity(SolicitudInstitucion s) {
        SolicitudInstitucionEntity e = new SolicitudInstitucionEntity();
        e.setId(s.getId());
        e.setNombreColegio(s.getNombreColegio());
        e.setNit(s.getNit());
        e.setCiudad(s.getCiudad());
        e.setDireccion(s.getDireccion());
        e.setTelefono(s.getTelefono());
        e.setNombreContacto(s.getNombreContacto());
        e.setCorreo(s.getCorreo());
        e.setMensaje(s.getMensaje());
        e.setEnviadaPorUsuarioId(s.getEnviadaPorUsuarioId());
        e.setEstado(s.getEstado());
        e.setRevisadaPorUsuarioId(s.getRevisadaPorUsuarioId());
        e.setFechaRevision(s.getFechaRevision());
        e.setMotivoRechazo(s.getMotivoRechazo());
        e.setConvertidaEnInstitucionId(s.getConvertidaEnInstitucionId());
        e.setFechaConversion(s.getFechaConversion());
        e.setCreatedAt(s.getCreatedAt());
        e.setUpdatedAt(s.getUpdatedAt());
        return e;
    }
}