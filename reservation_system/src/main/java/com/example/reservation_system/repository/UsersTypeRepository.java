package com.example.reservation_system.repository;

import com.example.reservation_system.entity.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersTypeRepository extends JpaRepository<UsersType, Integer> {

    @Query("SELECT u from UsersType u where u.userTypeName <> :userType")
    List<UsersType> findAllExcludingName(@Param("userType") String userType);
}
