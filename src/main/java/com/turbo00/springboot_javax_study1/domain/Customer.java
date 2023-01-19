package com.turbo00.springboot_javax_study1.domain;


import jakarta.persistence.Column;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
@SequenceGenerator(name = "idegen", sequenceName = "customer_seq", allocationSize = 1)
public class Customer extends Base {
    @Getter
    @Setter
    @Column(length = 100)
    private String name;

    @Getter
    @Setter
    @Column(length = 255)
    private String contact;

}
