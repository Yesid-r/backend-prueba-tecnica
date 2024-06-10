package com.example.pruebatecnica.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCredit {

    private Integer idDeudor;
    private Integer idCobrador;
    private Double saldo;


}
