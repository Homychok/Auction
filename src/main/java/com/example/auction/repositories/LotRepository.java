package com.example.auction.repositories;

import com.example.auction.dto.BidDTO;
import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findByLotStatusIs(String lotStatus);
    Lot findByLotTitleContainsIgnoreCase(String lotTitle);

}
