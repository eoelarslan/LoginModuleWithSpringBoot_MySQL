package com.login.module.model.base;


import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AutoIdValue {

    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Long id;


}
