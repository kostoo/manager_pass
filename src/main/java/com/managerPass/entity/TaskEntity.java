package com.managerPass.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import java.util.Objects;

@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NamedEntityGraph(name = "taskEntityGraph",
                  attributeNodes = {
                  @NamedAttributeNode(value = "userEntity"),
                  @NamedAttributeNode("priority")
                  }
)
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private Long idTask;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "message", length = 1024)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ToString.Exclude
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ToString.Exclude
    private PriorityEntity priority;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime dateTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime dateTimeFinish;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TaskEntity that = (TaskEntity) o;
        return idTask != null && Objects.equals(idTask, that.idTask);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
