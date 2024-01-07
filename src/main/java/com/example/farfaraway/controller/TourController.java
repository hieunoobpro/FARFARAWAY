package com.example.farfaraway.controller;

import com.example.farfaraway.entity.Tour;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.service.ImageService;
import com.example.farfaraway.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TourController {

    @Autowired
    private TourService tourService;
    @Autowired
    private ImageService imageService;

    @GetMapping
    public List<Tour> getAllTours() {
        return tourService.getAllTours();
    }
    //Tất cả tour cho admin
    @GetMapping("/tours")
    public String getToursPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                               Model model) {
        Page<Tour> pageData = tourService.getAllToursByPage(1,10);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/tour/index";
    }
    // Tạo User
    @GetMapping("/tour/create")
    public String getTourCreatePage() {
        return "admin/tour/create";
    }

    // Chi tiết bài viết
    @GetMapping("/tour/{id}/detail")
    public String getTourDetailPage(@PathVariable Integer id, Model model) {
        Tour tour = tourService.getTourById(id);
        model.addAttribute("tour", tour);
        model.addAttribute("imageList", imageService.getFilesOfCurrentUser());
        return "admin/tour/detail";
    }
}
