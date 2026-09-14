package org.esfe.servicios.implementaciones;

import org.esfe.dtos.proyecto.ProyectoSalida;
import org.esfe.dtos.tarea.TareaCambiarEstado;
import org.esfe.dtos.tarea.TareaGuardar;
import org.esfe.dtos.tarea.TareaModificar;
import org.esfe.dtos.tarea.TareaSalida;
import org.esfe.modelos.Proyecto;
import org.esfe.modelos.Tarea;
import org.esfe.repositorios.ITareaRepository;
import org.esfe.servicios.interfaces.ITareaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaRepository implements ITareaService {

    @Autowired
    private ITareaRepository tareaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TareaSalida> obtenerTodos() {
        List<Tarea> tareas = tareaRepository.findAll();

        return tareas.stream()
                .map(tarea -> modelMapper.map(tarea, TareaSalida.class))
                .toList();
    }

    @Override
    public Page<TareaSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Tarea> page = tareaRepository.findAll(pageable);

        List<TareaSalida> tareaDto = page.stream()
                .map(tarea -> modelMapper.map(tarea, TareaSalida.class))
                .toList();
        return new PageImpl<>(tareaDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public TareaSalida obtenerPorId(Integer id) {
        Optional<Tarea> tareas = tareaRepository.findById(id);

        return tareas.map(value -> modelMapper.map(value, TareaSalida.class)).orElse(null);
    }

    @Override
    public List<TareaSalida> obtenerPorProyectoId(Integer id) {
        List<Tarea> tareas = tareaRepository.findByProyectoId(id);
        return tareas.stream()
                .map(tarea -> modelMapper.map(tarea, TareaSalida.class))
                .toList();
    }

    @Override
    public TareaSalida crear(TareaGuardar tareaGuardar) {
        Tarea tarea = modelMapper.map(tareaGuardar, Tarea.class);
        tarea.setId(null);
        return modelMapper.map(tareaRepository.save(tarea), TareaSalida.class);
    }

    @Override
    public TareaSalida editar(TareaModificar tareaModificar) {
        Tarea tarea = tareaRepository.save(modelMapper.map(tareaModificar, Tarea.class));

        return modelMapper.map(tarea, TareaSalida.class);
    }

    @Override
    public TareaSalida cambiarEstado(TareaCambiarEstado tareaCambiarEstado) {
        Tarea tarea = tareaRepository.findById(tareaCambiarEstado.getId()).get();
        tarea.setEstado(Tarea.Status.valueOf(tareaCambiarEstado.getEstado()));

        return modelMapper.map(tareaRepository.save(tarea), TareaSalida.class);
    }

    @Override
    public void eliminarPorId(Integer id) {
        tareaRepository.deleteById(id);
    }
}
