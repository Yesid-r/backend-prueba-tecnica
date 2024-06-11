package com.example.pruebatecnica.service;

import com.example.pruebatecnica.model.Credito;
import com.example.pruebatecnica.model.DTO.PaymentDTO;
import com.example.pruebatecnica.model.DTO.RegisterCredit;
import com.example.pruebatecnica.repository.CreditRepository;
import com.example.pruebatecnica.user.User;
import com.example.pruebatecnica.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final UserRepository userRepository;

    public Object save(RegisterCredit registerCredit) {
        Credito credit = new Credito();
        Optional<User> deudor = userRepository.findById(registerCredit.getIdDeudor());
        Optional<User> cobrador = userRepository.findById(registerCredit.getIdCobrador());
        credit.setDeudor(deudor.get());
        credit.setCobrador(cobrador.get());
        credit.setSaldo(registerCredit.getSaldo());
        return creditRepository.save(credit);
    }

    public List<Credito> findAll() {
        return creditRepository.findAll();
    }

    public List<Credito> findByDeudor(User user) {
        return creditRepository.findByDeudor(user);
    }

    public List<Credito> findByCobrador(User user) {
        return creditRepository.findByCobrador(user);
    }

    public Credito findById(Integer id) {
        return creditRepository.findById(id).orElse(null);

    }

    public Credito payment(PaymentDTO paymentDTO) {
        Optional<Credito> creditoOptional = creditRepository.findById(paymentDTO.getId());
        if (creditoOptional.isPresent()){
            Credito credito = creditoOptional.get();
            if (paymentDTO.getAmount()<= credito.getSaldo()){
                credito.setSaldo(credito.getSaldo()-paymentDTO.getAmount());
                return creditRepository.save(credito);
            }
        }
        return null;
    }

    public void deleteById(Integer id) {
        creditRepository.deleteById(id);
    }
}
