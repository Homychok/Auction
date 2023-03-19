package com.example.auction.repositories;

import com.example.auction.models.LotsPic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LotsPicRepository extends JpaRepository <LotsPic, Long> {
    Optional<LotsPic> findLotsPicByLotId(Long lotId);
}
