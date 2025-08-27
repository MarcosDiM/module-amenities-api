package com.inncome.service.serviceImpl;

import com.inncome.Mapper.DiaHorarioMapper;
import com.inncome.dto.DiaHorario.DiaHorarioRequestDto;
import com.inncome.dto.DiaHorario.DiaHorarioResponseDto;
import com.inncome.exception.ResourceNotFoundException;
import com.inncome.model.Amenitie;
import com.inncome.model.DiaHorario;
import com.inncome.repository.DiaHorarioRepository;
import com.inncome.service.DiaHorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaHorarioServiceImpl implements DiaHorarioService {

    private final DiaHorarioRepository diaHorarioRepository;
    private final DiaHorarioMapper diaHorarioMapper;

    @Override
    public DiaHorario findDiaHorarioById(Long id) {
        return diaHorarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DiaHorario no encontrado con el id: " + id));
    }

    @Override
    public List<DiaHorarioResponseDto> listar() {
        return diaHorarioRepository.findAll()
                .stream()
                .map(diaHorarioMapper::toDiaHorarioResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DiaHorarioResponseDto actualizar(Long id, DiaHorarioRequestDto dto) {
        DiaHorario existente = diaHorarioRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("DiaHorario no encontrado con el id: " + id));

        existente.setDiaSemana(dto.getDiaSemana());
        existente.setHoraInicio(dto.getHoraInicio());
        existente.setHoraFin(dto.getHoraFin());
        existente.setActivo(dto.getActivo());

        diaHorarioRepository.save(existente);
        return diaHorarioMapper.toDiaHorarioResponseDto(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!diaHorarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe el dia y horario con ID: " + id);
        }
        diaHorarioRepository.deleteById(id);
    }

    @Override
    public DiaHorario agregarDiaHorario(Amenitie amenitie, DiaHorarioRequestDto dhDto) {
        DiaHorario diaHorario = new DiaHorario();
        diaHorario.setDiaSemana(dhDto.getDiaSemana());
        diaHorario.setHoraInicio(dhDto.getHoraInicio());
        diaHorario.setHoraFin(dhDto.getHoraFin());
        diaHorario.setAmenitie(amenitie);

        amenitie.getDiasHorarios().add(diaHorario);

        return diaHorarioRepository.save(diaHorario);
    }

    @Override
    public DiaHorario eliminarDiaHorario(Amenitie amenitie, Long diaHorarioId) {
        DiaHorario diaHorarioAEliminar = amenitie.getDiasHorarios().stream()
                .filter(dh -> dh.getId().equals(diaHorarioId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Día y horario no encontrado con id: " + diaHorarioId + " para el amenitie " + amenitie.getId()));

        diaHorarioAEliminar.setActivo(false);

        return diaHorarioRepository.save(diaHorarioAEliminar);
    }
}