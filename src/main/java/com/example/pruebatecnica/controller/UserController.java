package com.example.pruebatecnica.controller;

import com.example.pruebatecnica.config.auth.AuthenticationRequest;
import com.example.pruebatecnica.service.UserService;
import com.example.pruebatecnica.user.Role;
import com.example.pruebatecnica.user.User;
import com.example.pruebatecnica.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {


    private final UserService userService;

    @GetMapping()
    public ResponseEntity findAll () {
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("/find-usersrole/{role}")
    public ResponseEntity findByRole(@PathVariable String role){
        return ResponseEntity.ok(userService.findByRole(Role.valueOf(role)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){

        User userfound = userService.findById(id);
        if (userfound != null){
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity findUser(@PathVariable Integer id){

        User userfound = userService.findById(id);
        if (userfound != null){

            return ResponseEntity.ok(userfound);
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody UserDTO userDto){
        User userFound = userService.findById(id);
        if (userFound != null){
            User user = userService.updateUser(id, userDto);
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/change-password")
    public ResponseEntity updateUser(@RequestBody AuthenticationRequest authenticationRequest){
        User userFound = userService.findByEmail(authenticationRequest.getEmail());
        if (userFound != null){
            userService.updatePassword(authenticationRequest);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
