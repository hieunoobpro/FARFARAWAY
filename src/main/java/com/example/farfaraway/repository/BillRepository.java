package com.example.farfaraway.repository;

import com.example.farfaraway.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository  extends JpaRepository<Bill, Integer> {
}
