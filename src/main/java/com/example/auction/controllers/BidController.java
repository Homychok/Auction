package com.example.auction.controllers;

import com.example.auction.dto.BidDTO;
import com.example.auction.services.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bid")
public class BidController {
    private BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }
    /*
0.Создает нового участника ставок
*/
    @PostMapping
    public ResponseEntity<BidDTO> createNewBidder(@RequestBody BidDTO bidDTO) {
        BidDTO createNewBid = bidService.createBid(bidDTO);
        return ResponseEntity.ok(createNewBid);
    }
}
