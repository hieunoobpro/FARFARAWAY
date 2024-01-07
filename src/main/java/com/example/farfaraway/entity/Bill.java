package com.example.farfaraway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tourId;

    @Column(name = "price")
    private Float price;

    @Column(name = "dayTravel")
    private Float dayTravel;

    @ManyToOne
    @JoinColumn(name = "departureDate")
    private DepartureDate departureDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }
}
