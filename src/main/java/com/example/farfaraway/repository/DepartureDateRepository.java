package com.example.farfaraway.repository;


import com.example.farfaraway.entity.DepartureDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartureDateRepository extends JpaRepository<DepartureDate, Integer> {
   /*Optional<DepartureDate> findByDepartureDate(String date);*/
    List<DepartureDate> findDepartureDateByTourId (Integer id);
}
