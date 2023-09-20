package com.api.usuario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phones implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String number;

    @Column
    private String cityCode;

    @Column
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Users users;
}
