package backend.people.infrastructure.rest.mapper;

import backend.people.domain.model.Institucion;
import backend.people.infrastructure.rest.dto.InstitucionRequestDto;
import backend.people.infrastructure.rest.dto.InstitucionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class InstitucionRestMapper {

    public InstitucionResponseDto toResponse(Institucion i) {
        InstitucionResponseDto dto = new InstitucionResponseDto();
        dto.setId(i.getId());
        dto.setCodigo(i.getCodigo());
        dto.setNombre(i.getNombre());
        dto.setNombreCorto(i.getNombreCorto());
        dto.setTipoInstitucion(i.getTipoInstitucion());
        dto.setNit(i.getNit());
        dto.setDane(i.getDane());
        dto.setResolucionFuncionamiento(i.getResolucionFuncionamiento());
        dto.setDescripcion(i.getDescripcion());
        dto.setCorreoContacto(i.getCorreoContacto());
        dto.setTelefonoContacto(i.getTelefonoContacto());
        dto.setSitioWeb(i.getSitioWeb());
        dto.setDireccionPrincipal(i.getDireccionPrincipal());
        dto.setCiudad(i.getCiudad());
        dto.setDepartamento(i.getDepartamento());
        dto.setPais(i.getPais());
        dto.setLogoUrl(i.getLogoUrl());
        dto.setZonaHoraria(i.getZonaHoraria());
        dto.setMoneda(i.getMoneda());
        dto.setEstado(i.getEstado());
        return dto;
    }

    // copia solo lo que el cliente envia
    public Institucion toDomain(InstitucionRequestDto dto) {
        Institucion i = new Institucion();
        i.setCodigo(dto.getCodigo());
        i.setNombre(dto.getNombre());
        i.setNombreCorto(dto.getNombreCorto());
        i.setTipoInstitucion(dto.getTipoInstitucion());
        i.setNit(dto.getNit());
        i.setDane(dto.getDane());
        i.setResolucionFuncionamiento(dto.getResolucionFuncionamiento());
        i.setDescripcion(dto.getDescripcion());
        i.setCorreoContacto(dto.getCorreoContacto());
        i.setTelefonoContacto(dto.getTelefonoContacto());
        i.setSitioWeb(dto.getSitioWeb());
        i.setDireccionPrincipal(dto.getDireccionPrincipal());
        i.setCiudad(dto.getCiudad());
        i.setDepartamento(dto.getDepartamento());
        i.setPais(dto.getPais());
        i.setLogoUrl(dto.getLogoUrl());
        i.setZonaHoraria(dto.getZonaHoraria());
        i.setMoneda(dto.getMoneda());
        return i;
    }
}