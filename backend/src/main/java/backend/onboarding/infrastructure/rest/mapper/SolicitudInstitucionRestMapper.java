package backend.onboarding.infrastructure.rest.mapper;

import backend.onboarding.domain.model.SolicitudInstitucion;
import backend.onboarding.infrastructure.rest.dto.SolicitudInstitucionRequestDto;
import backend.onboarding.infrastructure.rest.dto.SolicitudInstitucionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class SolicitudInstitucionRestMapper {

    public SolicitudInstitucionResponseDto toResponse(SolicitudInstitucion s) {
        SolicitudInstitucionResponseDto dto = new SolicitudInstitucionResponseDto();
        dto.setId(s.getId());
        dto.setNombreColegio(s.getNombreColegio());
        dto.setNit(s.getNit());
        dto.setCiudad(s.getCiudad());
        dto.setDireccion(s.getDireccion());
        dto.setTelefono(s.getTelefono());
        dto.setNombreContacto(s.getNombreContacto());
        dto.setCorreo(s.getCorreo());
        dto.setMensaje(s.getMensaje());
        dto.setEstado(s.getEstado());
        dto.setConvertidaEnInstitucionId(s.getConvertidaEnInstitucionId());
        dto.setCreatedAt(s.getCreatedAt());
        return dto;
    }

    public SolicitudInstitucion toDomain(SolicitudInstitucionRequestDto dto) {
        SolicitudInstitucion s = new SolicitudInstitucion();
        s.setNombreColegio(dto.getNombreColegio());
        s.setNit(dto.getNit());
        s.setCiudad(dto.getCiudad());
        s.setDireccion(dto.getDireccion());
        s.setTelefono(dto.getTelefono());
        s.setNombreContacto(dto.getNombreContacto());
        s.setCorreo(dto.getCorreo());
        s.setMensaje(dto.getMensaje());
        return s;
    }
}