package com.example.auction.dto;

import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BidDTO {
    private String bidderName;
    private LocalDateTime bidDate;
    private Lot lotId;

    public static BidDTO fromBid (Bid bid) {
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidDate(bid.getBidDate());
        bidDTO.setBidderName(bid.getBidderName());
        bidDTO.setLotId(bid.getLotId());
        return bidDTO;
    }

    public Bid toBid() {
        Bid bid = new Bid();
        bid.setBidDate(this.getBidDate());
        bid.setBidderName(this.getBidderName());
        bid.setLotId(this.getLotId());
        return bid;
    }
}
