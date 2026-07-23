package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.Asistencia.MatrizAsistenciaConsultaRepository;
import backend.academic.infrastructure.persistence.MatrizAsistenciaJpaRepository;
import backend.academic.application.port.MarcaAsistenciaFila;
import backend.academic.application.port.SesionFechaFila;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MatrizAsistenciaConsultaRepositoryAdapter implements MatrizAsistenciaConsultaRepository {
    private final MatrizAsistenciaJpaRepository jpa;

    public MatrizAsistenciaConsultaRepositoryAdapter(MatrizAsistenciaJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<SesionFechaFila> sesionesDeCarga(UUID cargaId) {
        return jpa.sesionesDeCarga(cargaId.toString());
    }

    @Override
    public List<MarcaAsistenciaFila> marcasDeCarga(UUID cargaId) {
        return jpa.marcasDeCarga(cargaId.toString());
    }
}