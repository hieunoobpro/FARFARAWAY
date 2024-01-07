package com.example.farfaraway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String customerName;
    private String email;
    private String phone;
    private Date departureDate;
    private Integer numberOfTicket;
    @JoinColumn(name = "tour_id")
    private Integer tourId;
}
