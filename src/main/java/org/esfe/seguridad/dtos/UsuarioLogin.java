package org.esfe.seguridad.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLogin {
    private String login;
    private String clave;
}