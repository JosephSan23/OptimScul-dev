package backend.people.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.Institucion;
import backend.security.application.CorreoInstitucionalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarInstitucionUseCase {

    private final InstitucionRepository institucionRepository;
    private final CorreoInstitucionalService correoInstitucionalService;

    public EditarInstitucionUseCase(InstitucionRepository institucionRepository,
                                    CorreoInstitucionalService correoInstitucionalService) {
        this.institucionRepository = institucionRepository;
        this.correoInstitucionalService = correoInstitucionalService;
    }

    @Transactional
    public Institucion ejecutar(UUID id, Institucion datos) {

        Institucion existente = institucionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe una institución con el id " + id));

        if (!existente.getCodigo().equals(datos.getCodigo())
                && institucionRepository.existsByCodigo(datos.getCodigo())) {
            throw new RuntimeException("Ya existe una institución con el código " + datos.getCodigo());
        }

        String dominioAnterior = existente.getDominioCorreo();

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
        existente.setDominioCorreo(datos.getDominioCorreo());
        existente.setDireccionPrincipal(datos.getDireccionPrincipal());
        existente.setCiudad(datos.getCiudad());
        existente.setDepartamento(datos.getDepartamento());
        existente.setLogoUrl(datos.getLogoUrl());

        if (!esVacio(datos.getPais()))        existente.setPais(datos.getPais());
        if (!esVacio(datos.getZonaHoraria())) existente.setZonaHoraria(datos.getZonaHoraria());
        if (!esVacio(datos.getMoneda()))      existente.setMoneda(datos.getMoneda());

        existente.setUpdatedAt(LocalDateTime.now());

        Institucion guardada = institucionRepository.save(existente);

        // Si el dominio cambió, propaga a los correos institucionales de los usuarios del colegio
        String dominioNuevo = guardada.getDominioCorreo();
        if (dominioNuevo != null && !dominioNuevo.isBlank() && !dominioNuevo.equals(dominioAnterior)) {
            correoInstitucionalService.regenerarDominio(guardada.getId(), dominioNuevo);
        }

        return guardada;
    }

    private boolean esVacio(String s) {
        return s == null || s.isBlank();
    }
}