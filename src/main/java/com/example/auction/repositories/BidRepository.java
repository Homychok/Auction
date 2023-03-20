package com.example.auction.repositories;

import com.example.auction.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    @Query(value = "SELECT bidder_name AS bidderName, bid_date AS bidDate FROM bid GROUP BY id WHERE lot_id = ?2,id = ?1  ORDER BY bidder_date ASC LIMIT 1", nativeQuery = true)
    Long findByBidDateMin(Long id, Long lotId);
    @Query(value = "SELECT bidder_name, MAX(bidder_count) FROM (SELECT bidder_date, COUNT(bidder_date) bidder_count" +
            " FROM bid GROUP BY id WHERE lot_id = ?2, id = ?1)", nativeQuery = true)
    Long getMaxBiddersOfBidByLotId(Long id, Long lotId);

    List<Bid> findByBidderNameContainsIgnoreCase(String bidderName);
    List<Bid> findByBidDate(LocalDateTime bidDate);
    @Query(value = "SELECT bidder_name AS bidderName FROM bid GROUP BY id WHERE lot_id = ? ORDER BY bidder_date DESC LIMIT 1", nativeQuery = true)
    Bid findByBidDateMax(Long lotId);
    @Query(value = "SELECT COUNT(*) FROM bid WHERE lot_id = ?1", nativeQuery = true)
    Long getCountNumberOfBidByLotId(Long lotId);

}
