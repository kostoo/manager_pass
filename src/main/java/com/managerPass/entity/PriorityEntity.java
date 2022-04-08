package com.managerPass.entity;

import com.managerPass.entity.Enum.EPriority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "priority")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PriorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EPriority name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PriorityEntity priority = (PriorityEntity) o;
        return id != null && Objects.equals(id, priority.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
