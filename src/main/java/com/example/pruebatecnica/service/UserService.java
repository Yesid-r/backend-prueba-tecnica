package com.example.pruebatecnica.service;

import com.example.pruebatecnica.config.auth.AuthenticationRequest;
import com.example.pruebatecnica.user.User;
import com.example.pruebatecnica.user.UserDTO;
import com.example.pruebatecnica.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll () {
        return userRepository.findAll();
    }

    public User findById (Integer id){
        return userRepository.findById(id).orElseThrow();
    }
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Integer id, UserDTO userDto) {
        try {
            User user = findById(id);
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setPhone(userDto.getPhone());
            return userRepository.save(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }

    public void updatePassword(AuthenticationRequest authenticationRequest) {
        User user = findByEmail(authenticationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));

        userRepository.save(user);
    }
}
