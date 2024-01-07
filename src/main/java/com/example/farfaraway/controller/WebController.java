package com.example.farfaraway.controller;


import com.example.farfaraway.entity.Blog;
import com.example.farfaraway.entity.DepartureDate;
import com.example.farfaraway.entity.Tour;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.repository.DepartureDateRepository;
import com.example.farfaraway.service.BlogService;
import com.example.farfaraway.service.BookingService;
import com.example.farfaraway.service.CategoryService;
import com.example.farfaraway.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class WebController {
    @Autowired
    private final TourService tourService;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final BlogService blogService;
    @Autowired
    private DepartureDateRepository departureDateRepository;
    @Autowired
    private BookingService bookingService;


    @GetMapping("/")
   public String getHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Tour> pageData = tourService.getAllTours();
        model.addAttribute("tours", pageData);
        return "index";
    }

  // Xử lý tìm kiếm tour du lịch dựa trên tên
    @GetMapping("/search")
    public String searchTours(@RequestParam("name") String name, Model model) {
        List<Tour> searchResults = tourService.searchByName(name);
        model.addAttribute("tours", searchResults);
        return "search-results";
    }
    @GetMapping("/tour")
    public String getAllTour(Model model){
        List<Tour> allTour = tourService.getAllTours();
        model.addAttribute("allTour" , allTour);
        return "tour";
    }
    @GetMapping("/tour-detail")
    public String getAllTourDetail(@RequestParam("id") Integer id,Model model){
        Tour tour = tourService.getTourById(id);
        List<DepartureDate> departureDates = departureDateRepository.findDepartureDateByTourId(id);
        model.addAttribute("tour" , tour);
        model.addAttribute("departureDates", departureDates);
        return "tour-detail";
    }
    @GetMapping("/blog")
    public String getBlogPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<Blog> pageData = blogService.getAllBlogs(page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "blog";
    }

    @GetMapping("/blog-detail")
    public String showBlogDetail(@RequestParam("id") Integer id, Model model) {
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "blog-detail";
    }

    @GetMapping("/categories")
    public String getAllCategory(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

   @PostMapping("/book-tour")
    public String bookTour(@RequestParam("customerName") String customerName,
                           @RequestParam("tourId") Integer tourId,
                           @RequestParam("phone") String phone,
                           @RequestParam("email") String email,
                           @RequestParam("date") String date,
                           @RequestParam("numberOfGuests") Integer number,
                           Model model) {
       Tour tour = tourService.getTourById(tourId);
       bookingService.bookTour(customerName,tourId,email, phone, date, number);
       model.addAttribute("customerName", customerName);
       model.addAttribute("email", email);
       model.addAttribute("phone", phone);
       model.addAttribute("date", date);
       model.addAttribute("tourName", tour.getName());
       model.addAttribute("tourPrice", tour.getPrice()*number);
        return "payment";
    }

    @GetMapping("/categories/{categoryName}")
    public String getBlogsOfCategory() {
        return null;
    }

    @GetMapping("/blogs/{blogId}/{blogSlug}")
    public String getBlogDetail() {
        return null;
    }

}
/*   @PostMapping("/book-tour")
    public String bookTour(@RequestParam("customerName") String customerName,
                           @RequestParam("tourId") Integer tourId,
                           @RequestParam("phone") String phone,
                           @RequestParam("email") String email,
                           @RequestParam("date") String date,
                           Model model) {
        Tour tour = tourService.getTourById(tourId);
        bookingService.bookTour(customerName,tourId,email, phone, date);
        model.addAttribute("customerName", customerName);
        model.addAttribute("email", email);
        model.addAttribute("phone", phone);
        model.addAttribute("date", date);
        model.addAttribute("tour", tour);
        return "payment";
    }*/