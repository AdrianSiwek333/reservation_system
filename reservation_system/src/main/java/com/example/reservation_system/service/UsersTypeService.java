package com.example.reservation_system.service;

import com.example.reservation_system.entity.UsersType;
import com.example.reservation_system.repository.UsersTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> findAllExceptAdmin(){
        return usersTypeRepository.findAllExcludingName("ADMIN");
    }
}
