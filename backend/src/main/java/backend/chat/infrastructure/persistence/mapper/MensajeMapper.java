package backend.chat.infrastructure.persistence.mapper;

import backend.chat.domain.model.Mensaje;
import backend.chat.infrastructure.persistence.entity.MensajeEntity;
import org.springframework.stereotype.Component;

@Component
public class MensajeMapper {

    public Mensaje toDomain(MensajeEntity e) {
        if (e == null)
            return null;
        Mensaje d = new Mensaje();
        d.setId(e.getId());
        d.setConversacionId(e.getConversacionId());
        d.setRemitenteId(e.getRemitenteId());
        d.setContenido(e.getContenido());
        d.setLeido(e.isLeido());
        d.setCreatedAt(e.getCreatedAt());
        return d;
    }

    public MensajeEntity toEntity(Mensaje d) {
        if (d == null)
            return null;
        MensajeEntity e = new MensajeEntity();
        e.setId(d.getId());
        e.setConversacionId(d.getConversacionId());
        e.setRemitenteId(d.getRemitenteId());
        e.setContenido(d.getContenido());
        e.setLeido(d.isLeido());
        e.setCreatedAt(d.getCreatedAt());
        return e;
    }
}