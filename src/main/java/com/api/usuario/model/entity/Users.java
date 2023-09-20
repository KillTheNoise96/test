package com.api.usuario.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UK_EMAIL", columnNames = "email"))
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "users", cascade = CascadeType.ALL)
    private List<Phones> phones;

    @Column
    private Date created;

    @Column
    private Date modified;

    @Column
    private Date lastLogin;

    @Column
    private String token;

    @Column
    private Boolean active;
}
