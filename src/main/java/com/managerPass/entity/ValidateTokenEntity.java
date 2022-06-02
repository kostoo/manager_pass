package com.managerPass.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "validate_token")
public class ValidateTokenEntity {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long idToken;

    @NotBlank
    private String token;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    @ToString.Exclude
    private UserEntity userEntity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date expiryDate;

    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ValidateTokenEntity that = (ValidateTokenEntity) o;
        return idToken != null && Objects.equals(idToken, that.idToken);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
