package com.example.farfaraway.controller;

import com.example.farfaraway.entity.DepartureDate;
import com.example.farfaraway.repository.DepartureDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DepartureDateController {
    @Autowired
    private DepartureDateRepository departureDateRepository;

}
