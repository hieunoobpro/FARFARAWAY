package com.example.farfaraway.repository;

import com.example.farfaraway.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Integer> {
    Page<Tour> findByStatus(Boolean status, Pageable pageable);

    // 2. Tìm kiếm blog theo từ khóa chứa trong name
    List<Tour> findByNameContainingIgnoreCase(String title);

    /*Tour findByDepartureDates (Date departureDate);*/
}
