package com.example.auction.repositories;

import com.example.auction.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findByLotStatus(String lotStatus);
    Lot findByLotTitleContainsIgnoreCase(String lotTitle);
    @Query(value = "UPDATE lot SET lot_status = ? WHERE lot_id = ?)", nativeQuery = true)
    Lot updateLotStatus(Long lotId, String lotStatus);

    @Query(value = "SELECT lot_status FROM lot WHERE lot_id = ?)", nativeQuery = true)
    Lot findLotStatusByLotId(Long lotId);}
