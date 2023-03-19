package com.example.auction.repositories;

import com.example.auction.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByBidderNameContainsIgnoreCase(String bidderName);
    List<Bid> findByBidDate(LocalDateTime bidDate);
    @Query(value = "SELECT * FROM bid GROUP BY bidder_id WHERE lot_id = ?,bidder_id = ?  ORDER BY bidder_date ASC LIMIT 1", nativeQuery = true)
    Long findByBidDateMin(int bidderId, Long lotId);
    @Query(value = "SELECT * FROM bid GROUP BY bidder_id WHERE lot_id = ?,bidder_id = ?  ORDER BY bidder_date DESC LIMIT 1", nativeQuery = true)
    Long findByBidDateMax(int bidderId, Long lotId);
    @Query(value = "SELECT COUNT(bidder_date) FROM bid GROUP BY bidder_id WHERE lot_id = ?, bidder_id = ?", nativeQuery = true)
    Long getCountNumberOfBidByLotId(int bidderId, Long lotId);
    @Query(value = "SELECT bidder_name, MAX(bidder_count) FROM (SELECT bidder_date, COUNT(bidder_date) bidder_count" +
            " FROM bid GROUP BY bidder_id WHERE lot_id = ?, bidder_id = ?)", nativeQuery = true)
    Long getMaxBiddersOfBidByLotId(int bidderId, Long lotId);
}
