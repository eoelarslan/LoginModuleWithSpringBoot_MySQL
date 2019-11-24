package com.login.module.controller.dto.responsedto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private Long userId;
    private String email;
    private String name;
    private String surname;
}
