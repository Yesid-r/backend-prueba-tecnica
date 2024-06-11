package com.example.pruebatecnica.controller;

import com.example.pruebatecnica.model.Credito;
import com.example.pruebatecnica.model.DTO.PaymentDTO;
import com.example.pruebatecnica.model.DTO.RegisterCredit;
import com.example.pruebatecnica.service.CreditService;
import com.example.pruebatecnica.user.User;
import com.example.pruebatecnica.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/credit")
@CrossOrigin
public class CreditController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditService creditService;

    @GetMapping()
    public ResponseEntity findAll(){
        return ResponseEntity.ok(creditService.findAll());
    }
    @PostMapping()
    public ResponseEntity registerCredit (@RequestBody RegisterCredit registerCredit){
        Optional<User> deudor = userRepository.findById(registerCredit.getIdDeudor());
        Optional<User> cobrador = userRepository.findById(registerCredit.getIdCobrador());
        if (deudor.isPresent() && cobrador.isPresent()) {
            return ResponseEntity.ok(creditService.save(registerCredit));
        }else{
            throw new RuntimeException("Not found");
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity findCredit(@PathVariable Integer id){
        Credito credito = creditService.findById(id);
        if (credito != null){
            return ResponseEntity.ok(credito);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCredit(@PathVariable Integer id){
        Credito credito = creditService.findById(id);
        if (credito!= null){
            creditService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/credits-deudor/{id}")
    public ResponseEntity findByDeudor(@PathVariable Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            List<Credito> creditos = creditService.findByDeudor(user.get());
            return ResponseEntity.ok(creditos);
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/credits-cobrador/{id}")
    public ResponseEntity findByCobrador(@PathVariable Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            List<Credito> creditos = creditService.findByCobrador(user.get());
            return ResponseEntity.ok(creditos);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/payment")
    public ResponseEntity addPayment (@RequestBody PaymentDTO paymentDTO) {
        Credito credito = creditService.payment(paymentDTO);
        if (credito != null){
            return ResponseEntity.ok(credito);
        }
        return ResponseEntity.notFound().build();

    }
}
