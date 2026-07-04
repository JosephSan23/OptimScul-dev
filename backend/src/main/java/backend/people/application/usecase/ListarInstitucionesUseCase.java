package backend.people.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.Institucion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarInstitucionesUseCase {
    private final InstitucionRepository institucionRepository;

    public ListarInstitucionesUseCase(InstitucionRepository institucionRepository) {
        this.institucionRepository = institucionRepository;
    }

    public List<Institucion> ejecutar() {
        return institucionRepository.findAll();
    }


}
