package com.example.auction.dto;

import com.example.auction.models.Bid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BidDTO {
    private String bidderName;
    private LocalDateTime bidDate;
    private Long lotId;
//    private Integer startPrice;
//    private Integer bidPrice;

    public static BidDTO fromBidToBidDTO (Bid bid) {
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidDate(bid.getBidDate());
        bidDTO.setBidderName(bid.getBidderName());
//        bidDTO.setStartPrice(bid.getLot().getStartPrice());
//        bidDTO.setBidPrice(bid.getLot().getBidPrice());
        bidDTO.setLotId(bid.getLot().getId());
        return bidDTO;
    }

    public Bid fromBidDTOToBid(BidDTO bidDTO) {
        Bid bid = new Bid();
        bid.setBidDate(bidDTO.getBidDate());
        bid.setBidderName(bidDTO.getBidderName());
        return bid;
    }
}
