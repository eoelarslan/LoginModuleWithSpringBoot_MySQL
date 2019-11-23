package com.login.module.model;

import com.login.module.model.base.DataEntity;
import com.login.module.validator.constraint.ValidPassword;
import lombok.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by ersin on 21.11.2019.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends DataEntity implements Serializable{

    @NotBlank(message = "Please enter the name.")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Please enter the surname.")
    @Column(name = "surname", nullable = false)
    private String surname;


    @NotBlank(message = "Please enter an email.")
    @Email(message = "Invalid email.", regexp = "^(.+)@(.+)$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Please enter the postal address.")
    @Column(name = "postal_address", nullable = false)
    private String postalAddress;

    @Column(name = "user_status", nullable = false)
    private int userStatusCode;

    @Column(name = "login_module_code", nullable = false)
    private int loginModuleCode;

    @Column(name = "created_ts", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_ts")
    private LocalDate updatedTime;

    @Column(name = "mail_sent_ts")
    private LocalDateTime mailSentTime;

    @Column(name = "authenticationToken")
    private String authenticationToken;


    @Size(min = 8, max = 30)
    @ValidPassword
    @Column(name = "password", nullable = false)
    private String password;
}
