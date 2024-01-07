package com.example.farfaraway.service;

import com.example.farfaraway.entity.Blog;
import com.example.farfaraway.entity.Tour;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.exception.NotFoundException;
import com.example.farfaraway.repository.TourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourService {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public Page<Tour> findAll(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("name").descending());
        return tourRepository.findByStatus(true, pageable);
    }
    public List<Tour> getTourByName(String name) {
        return tourRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Tour> searchByName(String name) {
        // Triển khai logic tìm kiếm tour dựa trên 'name' trong repository
        return tourRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }
    public Tour getTourById(Integer id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found blog with id = " + id));
    }
    public Page<Tour> getAllToursByPage(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return tourRepository.findAll(pageable);
    }
}
