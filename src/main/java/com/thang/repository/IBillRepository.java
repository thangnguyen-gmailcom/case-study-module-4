package com.thang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thang.model.Bill;

public interface IBillRepository extends JpaRepository<Bill, Integer> {

}
