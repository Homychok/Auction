package com.example.auction.repositories;

import com.example.auction.dto.BidDTOForFullLotDTO;
import com.example.auction.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BidRepository extends JpaRepository<Bid, Long> {
    @Query(value = "SELECT bidder_name AS bidderName, bid_date AS bidDate AS lotId FROM bid AS new_bid WHERE new_bid.lot_id = ? ORDER BY bid_date ASC LIMIT 1", nativeQuery = true)
    Bid findByBidDateMin(Long lotId);
    @Query(value = "SELECT bidder_name AS bidderName, bid_date AS bidDate FROM bid WHERE lot_id = ? ORDER BY bid_date DESC LIMIT 1", nativeQuery = true)
    Bid findByBidDateMax(Long lotId);
    @Query(value = "SELECT COUNT(*) FROM bid WHERE lot_id = ?1", nativeQuery = true)
    Long getCountNumberOfBidByLotId(Long lotId);
    @Query(value = "SELECT bidder_name AS bidderName, MAX(new_bid.bidder_count) AS max_count, MAX(last_bid) " +
            "FROM (SELECT bidder_name, COUNT(bid_date) AS bidder_count, MAX(bid_date) AS last_bid" +
            " FROM bid WHERE lot_id = ? GROUP BY bidder_name) AS new_bid GROUP BY bidder_name" +
            " ORDER BY max_count DESC LIMIT 1", nativeQuery = true )
    Bid getMaxBidders(Long lotId);


}
