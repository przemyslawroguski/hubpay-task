package com.hubpay.task.interview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.hubpay.task.interview.model.entities.CustomerEntity;
import com.hubpay.task.interview.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    private final CustomerEntity testCustomer = CustomerEntity.builder()
                    .username("testUsername")
                    .build();

    @Test
    public void testCreateCustomer() {
        when(repository.save(any(CustomerEntity.class))).thenReturn(testCustomer);

        CustomerEntity created = service.createCustomer(testCustomer);

        assertNotNull(created);
        assertEquals("testUsername", created.getUsername());
        verify(repository).save(any(CustomerEntity.class));
    }

    @Test
    public void testFindByUsernameFound() {
        String username = "testUsername";
        when(repository.findByUsername(username)).thenReturn(Optional.of(testCustomer));

        CustomerEntity found = service.findByUsername(username);

        assertNotNull(found);
        assertEquals(username, found.getUsername());
    }

    @Test
    public void testFindByUsernameNotFound() {
        String username = "nonExistingCustomer";
        when(repository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.findByUsername(username);
        });
    }
    
}
