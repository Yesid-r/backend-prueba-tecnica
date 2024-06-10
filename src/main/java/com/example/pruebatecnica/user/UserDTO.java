package com.example.pruebatecnica.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
