package com.example.reservation_system.service;

import com.example.reservation_system.entity.HostProfile;
import com.example.reservation_system.entity.Property;
import com.example.reservation_system.repository.HostProfileRepository;
import com.example.reservation_system.repository.PropertyRepository;
import com.example.reservation_system.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    PropertyRepository propertyRepository;
    HostProfileRepository hostProfileRepository;
    UsersRepository usersRepository;

    public PropertyService(PropertyRepository propertyRepository, HostProfileRepository hostProfileRepository, UsersRepository usersRepository) {
        this.propertyRepository = propertyRepository;
        this.hostProfileRepository = hostProfileRepository;
        this.usersRepository = usersRepository;
    }

    public List<Property> findByHostProfile(HostProfile hostProfile) {
        return propertyRepository.findByHostId(hostProfile);
    }

    public void update(Property property) {
        propertyRepository.save(property);
    }

    public Optional<Property> findById(int id){
        return propertyRepository.findById(id);
    }

    public List<Property> findRandom6Properties(){
        return propertyRepository.findRandom6Properties();
    }
}
