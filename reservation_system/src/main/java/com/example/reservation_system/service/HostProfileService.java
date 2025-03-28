package com.example.reservation_system.service;

import com.example.reservation_system.entity.HostProfile;
import com.example.reservation_system.repository.HostProfileRepository;
import com.example.reservation_system.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HostProfileService {

    private final HostProfileRepository hostProfileRepository;
    private final UsersRepository usersRepository;

    public HostProfileService(HostProfileRepository hostProfileRepository, UsersRepository usersRepository) {
        this.hostProfileRepository = hostProfileRepository;
        this.usersRepository = usersRepository;
    }

    public void update(HostProfile hostProfile) {
        hostProfileRepository.save(hostProfile);
    }

    public Optional<HostProfile> findById(int id){
        return hostProfileRepository.findById(id);
    }
}
