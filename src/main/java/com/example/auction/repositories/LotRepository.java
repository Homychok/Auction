package com.example.auction.repositories;

import com.example.auction.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findLotByLotId(int lotId);
}
