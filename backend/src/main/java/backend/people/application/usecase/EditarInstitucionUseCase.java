package backend.people.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.Institucion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarInstitucionUseCase {

    private final InstitucionRepository institucionRepository;

    public EditarInstitucionUseCase(InstitucionRepository institucionRepository) {
        this.institucionRepository = institucionRepository;
    }

    public Institucion ejecutar(UUID id, Institucion datos) {

        // 1. Buscar la que se quiere editar (o error si no existe)
        Institucion existente = institucionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe una institución con el id " + id));

        // 2. Si el código cambió, validar que no choque con OTRA institución
        if (!existente.getCodigo().equals(datos.getCodigo())
                && institucionRepository.existsByCodigo(datos.getCodigo())) {
            throw new RuntimeException("Ya existe una institución con el código " + datos.getCodigo());
        }

        // 3. Actualizar los campos editables
        existente.setCodigo(datos.getCodigo());
        existente.setNombre(datos.getNombre());
        existente.setNombreCorto(datos.getNombreCorto());
        existente.setTipoInstitucion(datos.getTipoInstitucion());
        existente.setNit(datos.getNit());
        existente.setDane(datos.getDane());
        existente.setResolucionFuncionamiento(datos.getResolucionFuncionamiento());
        existente.setDescripcion(datos.getDescripcion());
        existente.setCorreoContacto(datos.getCorreoContacto());
        existente.setTelefonoContacto(datos.getTelefonoContacto());
        existente.setSitioWeb(datos.getSitioWeb());
        existente.setDireccionPrincipal(datos.getDireccionPrincipal());
        existente.setCiudad(datos.getCiudad());
        existente.setDepartamento(datos.getDepartamento());
        existente.setLogoUrl(datos.getLogoUrl());

        // Campos NOT NULL: solo se cambian si vino un valor
        if (!esVacio(datos.getPais()))        existente.setPais(datos.getPais());
        if (!esVacio(datos.getZonaHoraria())) existente.setZonaHoraria(datos.getZonaHoraria());
        if (!esVacio(datos.getMoneda()))      existente.setMoneda(datos.getMoneda());

        // Se preservan a propósito: id, estado, createdAt
        existente.setUpdatedAt(LocalDateTime.now());

        return institucionRepository.save(existente);
    }

    private boolean esVacio(String s) {
        return s == null || s.isBlank();
    }
}