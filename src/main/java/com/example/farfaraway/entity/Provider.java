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
@ToString
@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String description;
    private Boolean statics;
    private String avatar;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}

    //Table nhacungcap {
    //id integer [primary key]
    //tenNCC varchar
    //username varchar
    //password varchar
    //moTa varchar
    //hinhAnh varchar
    //video varchar  ???
    //trangThai integer
    //created_at timestamp
    //updated_at timestamp
    //deleted_at timestamp
    //}

