package com.example.farfaraway.service;

import com.example.farfaraway.entity.*;
import com.example.farfaraway.exception.NotFoundException;
import com.example.farfaraway.repository.BookingRepository;
import com.example.farfaraway.repository.DepartureDateRepository;
import com.example.farfaraway.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private TourRepository tourRepository;

   @Autowired
    private BookingRepository bookingRepository;

   private List<Booking> findAllBooking(){
      return bookingRepository.findAll();
    }


   public void bookTour(String customerName,Integer tourId,String email, String phone, String departureDate, Integer numberOfTicket) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid departure date"));
       String format = "yyyy-MM-dd HH:mm:ss.S"; // Định dạng của chuỗi
       Booking booking = new Booking();
       DateFormat dateFormat = new SimpleDateFormat(format);
       try {
           Date date = dateFormat.parse(departureDate);
           System.out.println("Date: " + date);
           booking.setCustomerName(customerName);
           booking.setTourId(tourId);
           booking.setEmail(email);
           booking.setPhone(phone);
           booking.setDepartureDate(date);
           booking.setNumberOfTicket(numberOfTicket);
           bookingRepository.save(booking);
       } catch (ParseException e) {
           e.printStackTrace();
       }
    }
    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Page<Booking> getAllBookingByPage(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return bookingRepository.findAll(pageable);
    }
}
