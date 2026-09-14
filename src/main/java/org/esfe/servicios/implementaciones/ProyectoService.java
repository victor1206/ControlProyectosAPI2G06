package org.esfe.servicios.implementaciones;

import org.esfe.dtos.categoria.CategoriaSalida;
import org.esfe.dtos.proyecto.ProyectoGuardar;
import org.esfe.dtos.proyecto.ProyectoModificar;
import org.esfe.dtos.proyecto.ProyectoSalida;
import org.esfe.modelos.Categoria;
import org.esfe.modelos.Proyecto;
import org.esfe.repositorios.IProyectoRepositoy;
import org.esfe.servicios.interfaces.IProyectoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService implements IProyectoService {

    @Autowired
    private IProyectoRepositoy proyectoRepositoy;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProyectoSalida> obtenerTodos() {
        List<Proyecto> proyectos = proyectoRepositoy.findAll();

        return proyectos.stream()
                .map(proyecto -> modelMapper.map(proyecto, ProyectoSalida.class))
                .toList();
    }

    @Override
    public Page<ProyectoSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Proyecto> page = proyectoRepositoy.findAll(pageable);

        List<ProyectoSalida> proyectoDto = page.stream()
                .map(proyecto -> modelMapper.map(proyecto, ProyectoSalida.class))
                .toList();
        return new PageImpl<>(proyectoDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public ProyectoSalida obtenerPorId(Integer id) {
        Optional<Proyecto> proyecto = proyectoRepositoy.findById(id);

        return proyecto.map(value -> modelMapper.map(value, ProyectoSalida.class)).orElse(null);
    }

    @Override
    public ProyectoSalida crear(ProyectoGuardar proyectoGuardar) {
        Proyecto proyecto = modelMapper.map(proyectoGuardar, Proyecto.class);
        proyecto.setId(null);
        return modelMapper.map(proyectoRepositoy.save(proyecto), ProyectoSalida.class);
    }

    @Override
    public ProyectoSalida editar(ProyectoModificar proyectoModificar) {
        Proyecto proyecto = proyectoRepositoy.save(modelMapper.map(proyectoModificar, Proyecto.class));

        return modelMapper.map(proyecto, ProyectoSalida.class);
    }

    @Override
    public void eliminarPorId(Integer id) {
        proyectoRepositoy.deleteById(id);
    }
}
