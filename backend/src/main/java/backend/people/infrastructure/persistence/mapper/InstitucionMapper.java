package backend.people.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import backend.people.domain.model.Institucion;
import backend.people.infrastructure.persistence.entity.InstitucionEntity;

@Component
public class InstitucionMapper {

    public Institucion toDomain(InstitucionEntity entity) {
        if (entity == null) return null;
        Institucion domain = new Institucion();
        domain.setId(entity.getId());
        domain.setCodigo(entity.getCodigo());
        domain.setNombre(entity.getNombre());
        domain.setNombreCorto(entity.getNombreCorto());
        domain.setTipoInstitucion(entity.getTipoInstitucion());
        domain.setNit(entity.getNit());
        domain.setDane(entity.getDane());
        domain.setResolucionFuncionamiento(entity.getResolucionFuncionamiento());
        domain.setDescripcion(entity.getDescripcion());
        domain.setCorreoContacto(entity.getCorreoContacto());
        domain.setTelefonoContacto(entity.getTelefonoContacto());
        domain.setSitioWeb(entity.getSitioWeb());
        domain.setDireccionPrincipal(entity.getDireccionPrincipal());
        domain.setCiudad(entity.getCiudad());
        domain.setDepartamento(entity.getDepartamento());
        domain.setPais(entity.getPais());
        domain.setLogoUrl(entity.getLogoUrl());
        domain.setZonaHoraria(entity.getZonaHoraria());
        domain.setMoneda(entity.getMoneda());
        domain.setEstado(entity.getEstado());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public InstitucionEntity toEntity(Institucion domain) {
        if (domain == null) return null;
        InstitucionEntity entity = new InstitucionEntity();
        entity.setId(domain.getId());
        entity.setCodigo(domain.getCodigo());
        entity.setNombre(domain.getNombre());
        entity.setNombreCorto(domain.getNombreCorto());
        entity.setTipoInstitucion(domain.getTipoInstitucion());
        entity.setNit(domain.getNit());
        entity.setDane(domain.getDane());
        entity.setResolucionFuncionamiento(domain.getResolucionFuncionamiento());
        entity.setDescripcion(domain.getDescripcion());
        entity.setCorreoContacto(domain.getCorreoContacto());
        entity.setTelefonoContacto(domain.getTelefonoContacto());
        entity.setSitioWeb(domain.getSitioWeb());
        entity.setDireccionPrincipal(domain.getDireccionPrincipal());
        entity.setCiudad(domain.getCiudad());
        entity.setDepartamento(domain.getDepartamento());
        entity.setPais(domain.getPais());
        entity.setLogoUrl(domain.getLogoUrl());
        entity.setZonaHoraria(domain.getZonaHoraria());
        entity.setMoneda(domain.getMoneda());
        entity.setEstado(domain.getEstado());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
