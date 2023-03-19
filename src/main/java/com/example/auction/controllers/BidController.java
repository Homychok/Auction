package com.example.auction.controllers;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.models.Bid;
import com.example.auction.services.BidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("bidders")
public class BidController {
    private BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("{bidderId}")// GET http://localhost:8080/bidders/3
    public ResponseEntity<BidDTO> getBid(@PathVariable Long bidderId) {
        BidDTO bid = bidService.getBidderById(bidderId);
        if (bid == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(bid);
    }

    @GetMapping // GET http://localhost:8080/bidders
    public ResponseEntity<Collection<BidDTO>> getBidders(@RequestParam(required = false) LocalDateTime bidDate) {
        if (bidDate != null) {
            return ResponseEntity.ok(bidService.getBidderByDate(bidDate));
        }
        return ResponseEntity.ok(bidService.getAllBidders());
    }

    @GetMapping("{bidderId}/lots") // GET http://localhost:8080/bidders/3/lots
    public ResponseEntity<LotDTO> getLotByBidderId(@PathVariable Long bidderId) {
        return ResponseEntity.ok(bidService.getLotByBidderId(bidderId));
    }
    @GetMapping("/firstBidder")
    public ResponseEntity<Long> getFirstBidderByLotId(int bidderId, Long lotId) {
        return ResponseEntity.ok(bidService.getFirstBidderByLotId(bidderId, lotId));
    }
    @GetMapping("/lastBidder")
    public ResponseEntity<Long> getLastBidderByLotId(int bidderId, Long lotId) {
        return ResponseEntity.ok(bidService.getLastBidderByLotId(bidderId, lotId));
    }
    @GetMapping("/countBidder")
    public ResponseEntity<Long> getSumStudentAge(int bidderId, Long lotId) {
        return ResponseEntity.ok(bidService.getCountNumberOfBidByLotId(bidderId, lotId));
    }

    @PostMapping// POST http://localhost:8080/bidders
    public ResponseEntity<BidDTO> createNewBidder(@RequestBody BidDTO bidDTO) {
        BidDTO createNewBidder = bidService.createNewBidder(bidDTO);
        return ResponseEntity.ok(createNewBidder);
    }
    @PutMapping// PUT http://localhost:8080/bidders
    public ResponseEntity<BidDTO> updateInfoAboutBidder(@RequestBody BidDTO bidDTO) {
        BidDTO updateInfoAboutBidder = bidService.updateInfoAboutBidder(bidDTO);
        if (updateInfoAboutBidder == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateInfoAboutBidder);
    }

    @DeleteMapping("{bidderId}")// DELETE http://localhost:8080/bidders/3
    public ResponseEntity<Bid> deleteBidderById(@PathVariable Long bidderId) {
        bidService.deleteBidderById(bidderId);
        return ResponseEntity.ok().build();
    }
}
