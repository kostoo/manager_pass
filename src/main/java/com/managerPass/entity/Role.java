package com.managerPass.entity;


import com.managerPass.entity.Enum.ERole;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
