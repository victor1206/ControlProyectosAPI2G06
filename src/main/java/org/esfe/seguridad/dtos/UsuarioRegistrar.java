package org.esfe.seguridad.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRegistrar {
    private String nombre;
    private String apellido;
    private String telefono;
    private String login;
    private String clave;
    private Integer rolId;
}