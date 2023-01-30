package com.turbo00.springboot_javax_study1.domain.para;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "supplier")
@SequenceGenerator(name = "idegen", sequenceName = "supplier_seq", allocationSize = 1)
public class Supplier {
    @Getter
    @Setter
    @Column(length = 100, unique = true)
    private String name;

    @Getter
    @Setter
    @Column(length = 255)
    private String contact;

    @Getter
    @Setter
    @Column(length = 255)
    private String address;
}
