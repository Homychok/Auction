package com.example.auction.repositories;

import com.example.auction.enums.LotStatus;
import com.example.auction.models.Lot;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findAllByStatus(LotStatus lotStatus, PageRequest pageRequest);
    }
