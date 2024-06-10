package com.example.pruebatecnica.repository;

import com.example.pruebatecnica.model.Credito;
import com.example.pruebatecnica.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credito, Integer> {
    List<Credito> findByDeudor(User deudor);
    List<Credito> findByCobrador(User cobrador);
}
