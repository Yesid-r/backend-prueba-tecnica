package com.example.pruebatecnica.model;

import com.example.pruebatecnica.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credito")
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "deudor_id")
    private User deudor;

    @ManyToOne
    @JoinColumn(name = "cobrador_id")
    private User cobrador;

    private Double saldo;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @PrePersist
    private void beforePersist(){
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    private void beforeUpdate(){
        this.updatedAt = LocalDate.now();
    }
}
