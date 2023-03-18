package com.example.auction.repositories;

import com.example.auction.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
//    @Query(value = "SELECT * FROM bid ", nativeQuery = true)
//    Bid findByBidDate_Min(LocalDateTime bidDate);
//    Bid findByLotLastBid_Max(LocalDateTime bidDate);
    List<Bid> findByBidderNameContainsIgnoreCase(String bidderName);
    List<Bid> findByBidDate(LocalDateTime bidDate);
//    Optional<Bid> findBidByLotId(Long lotId);
@Query(value = "SELECT * FROM bid ORDER BY age ASC LIMIT 5", nativeQuery = true)
Long findByBidDate_Min();
    @Query(value = "SELECT * FROM student ORDER BY age ASC LIMIT 5", nativeQuery = true)
    Collection<Student> getFiveYoungestStudents();

}
