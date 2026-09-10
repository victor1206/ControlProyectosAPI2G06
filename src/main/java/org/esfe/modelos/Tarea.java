package org.esfe.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(length = 60, nullable = false)
    private String duracion;

    @Enumerated(EnumType.STRING)
    private Status estado;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;

    public static enum Status{
        APROBADA, PROCESO, REVISION, FINALIZADA
    }
}
