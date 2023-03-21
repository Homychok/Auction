package com.example.auction.repositories;

import com.example.auction.dto.BidDTOForFullLotDTO;
import com.example.auction.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    @Query(value = "SELECT bidder_name AS bidderName, bid_date AS bidDate FROM bid WHERE lot_id = ?2,id = ?1 GROUP BY id  ORDER BY bid_date ASC LIMIT 1", nativeQuery = true)
    Long findByBidDateMin(Long id, Long lotId);
    @Query(value = "SELECT bidder_name AS bidderName, MAX(bidder_count) FROM (SELECT bid_date, COUNT(bid_date) AS bidder_count" +
            " FROM bid WHERE lot_id = ?2, id = ?1 GROUP BY id )", nativeQuery = true)
    Long getMaxBiddersOfBidByLotId(Long id, Long lotId);
    @Query(value = "SELECT bidder_name AS bidderName, bid_date AS bidDame FROM bid WHERE lot_id = ?2 GROUP BY id ORDER BY bid_date DESC LIMIT 1", nativeQuery = true)
    BidDTOForFullLotDTO findByBidDateMax(Long lotId);
    @Query(value = "SELECT COUNT(*) FROM bid WHERE lot_id = ?", nativeQuery = true)
    Long getCountNumberOfBidByLotId(Long lotId);
    List<Bid> findByBidderNameContainsIgnoreCase(String bidderName);
    List<Bid> findByBidDate(LocalDateTime bidDate);
    @Query(value = "SELECT bidder_name AS bidderName, MAX(new_bid.bidder_count) AS max_count, MAX(last_bid) " +
            "FROM (SELECT bidder_name, COUNT(bid_date) AS bidder_count, MAX(bid_date) AS last_bid" +
            " FROM bid WHERE lot_id = ? GROUP BY bidder_name) AS new_bid GROUP BY bidder_name" +
            " ORDER BY max_count DESC LIMIT 1", nativeQuery = true )
    String getMaxBidders(Long lotId);


}
