package com.example.reservation_system.service;

import com.example.reservation_system.entity.HostProfile;
import com.example.reservation_system.entity.Property;
import com.example.reservation_system.repository.HostProfileRepository;
import com.example.reservation_system.repository.PropertyRepository;
import com.example.reservation_system.repository.UsersRepository;
import com.example.reservation_system.specification.PropertySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public int calculatePrice(int PropertyId, LocalDate startDate, LocalDate endDate){
        int between = (int)ChronoUnit.DAYS.between(startDate, endDate);
        return propertyRepository.calculatePrice(PropertyId, between);
    }

    public Page<Property> getProperties(String query, Integer minPrice, Integer maxPrice, LocalDate startDate,
                                        LocalDate endDate, Pageable pageable) {
        Specification<Property> spec = Specification.where(PropertySpecification.hasQuery(query))
                .and(PropertySpecification.hasPriceRange(minPrice, maxPrice))
                .and(PropertySpecification.hasDateRange(startDate, endDate));

        return propertyRepository.findAll(spec, pageable);
    }
}
