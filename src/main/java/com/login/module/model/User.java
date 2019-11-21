package com.login.module.model;

import com.login.module.model.base.DataEntity;
import lombok.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;


    @NotBlank
    @Email(message = "", regexp = "")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "postal_address", nullable = false)
    private String postalAddress;

    @Column(name = "user_status", nullable = false)
    private int userStatusCode;

    @Column(name = "login_module_code", nullable = false)
    private int loginModuleCode;

    @Column(name = "created_ts", nullable = false)
    private LocalDate createdTime;

    @Column(name = "updated_ts")
    private LocalDate updatedTime;

    @Column(name = "mail_sent_ts")
    private LocalDate mailSentTime;

    @Column(name = "authentication_token")
    private String authentication_token;

    @Column(name = "password", nullable = false)
    private String password;
}
