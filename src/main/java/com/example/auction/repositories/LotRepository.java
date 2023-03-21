package com.example.auction.repositories;

import com.example.auction.enums.LotStatus;
import com.example.auction.models.Lot;
import liquibase.pro.packaged.L;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findAllByStatus(LotStatus lotStatus, PageRequest pageRequest);
//    List<Lot> findLotByStatusAndPage(String status, Integer pageNumber);
//    Lot findByTitleContainsIgnoreCase(String title);
//    @Query(value = "UPDATE lot SET status = ?2 WHERE id = ?1)", nativeQuery = true)
//    Lot updateLotStatus(Long id, String status);
//
//    @Query(value = "SELECT status FROM lot WHERE id = ?)", nativeQuery = true)
//    Lot findLotStatusByLotId(Long id);
    }
