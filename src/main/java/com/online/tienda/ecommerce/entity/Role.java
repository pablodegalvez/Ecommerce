package com.online.tienda.ecommerce.entity;


import com.online.tienda.ecommerce.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "role")
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "roletype")
    private RoleType roleType;

}
