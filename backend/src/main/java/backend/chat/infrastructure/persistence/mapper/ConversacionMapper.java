package backend.chat.infrastructure.persistence.mapper;

import backend.chat.domain.model.Conversacion;
import backend.chat.infrastructure.persistence.entity.ConversacionEntity;
import org.springframework.stereotype.Component;

@Component
public class ConversacionMapper {

    public Conversacion toDomain(ConversacionEntity e) {
        if (e == null) return null;
        Conversacion d = new Conversacion();
        d.setId(e.getId());
        d.setInstitucionId(e.getInstitucionId());
        d.setUsuarioMenorId(e.getUsuarioMenorId());
        d.setUsuarioMayorId(e.getUsuarioMayorId());
        d.setCreatedBy(e.getCreatedBy());
        d.setCreatedAt(e.getCreatedAt());
        d.setUpdatedAt(e.getUpdatedAt());
        return d;
    }

    public ConversacionEntity toEntity(Conversacion d) {
        if (d == null) return null;
        ConversacionEntity e = new ConversacionEntity();
        e.setId(d.getId());
        e.setInstitucionId(d.getInstitucionId());
        e.setUsuarioMenorId(d.getUsuarioMenorId());
        e.setUsuarioMayorId(d.getUsuarioMayorId());
        e.setCreatedBy(d.getCreatedBy());
        e.setCreatedAt(d.getCreatedAt());
        e.setUpdatedAt(d.getUpdatedAt());
        return e;
    }
}