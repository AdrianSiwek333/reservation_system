package com.example.reservation_system.service;

import com.example.reservation_system.entity.HostProfile;
import com.example.reservation_system.repository.HostProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HostProfileService {

    private final HostProfileRepository hostProfileRepository;

    public HostProfileService(HostProfileRepository hostProfileRepository) {
        this.hostProfileRepository = hostProfileRepository;
    }

    public void update(HostProfile hostProfile) {
        hostProfileRepository.save(hostProfile);
    }

    public Optional<HostProfile> findById(int id){
        return hostProfileRepository.findById(id);
    }
}
