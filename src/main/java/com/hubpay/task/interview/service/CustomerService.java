package com.hubpay.task.interview.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hubpay.task.interview.model.entities.CustomerEntity;
import com.hubpay.task.interview.repository.CustomerRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;


    public CustomerEntity createCustomer(CustomerEntity customer) {
        log.info("saving customer with username: ", customer.getUsername());

        return this.repository.save(customer);
    }

    public CustomerEntity findByUsername(String username) {
        log.info("finding customer with username: ", username);

        return this.repository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with username not found: " + username));
    }
}
