package com.example.auction.repositories;

import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Id;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
//    @Query(value = "SELECT * FROM bid ", nativeQuery = true)
    String findBidByBidDate_Min();
    Integer findBidByLotLastBid_Max();
    List<Bid> findBidByLotId(int lotId);

}
