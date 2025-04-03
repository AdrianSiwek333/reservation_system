package com.example.reservation_system.service;

import com.example.reservation_system.entity.CustomerProfile;
import com.example.reservation_system.repository.CustomerProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerProfileService {

private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    public Optional<CustomerProfile> findById(int id){
        return customerProfileRepository.findById(id);
    }

    public void addNew(CustomerProfile customerProfile) {
        customerProfileRepository.save(customerProfile);
    }
}

