package com.example.auction.dto;

import com.example.auction.models.Bid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BidDTO {
    private Long id;
    private String bidderName;
    private LocalDateTime bidDate;
    private Long lotId;
//    private Integer startPrice;
//    private Integer bidPrice;
public static BidDTO fromBidToBidDTO(Bid bid) {
    BidDTO dto = new BidDTO();
    dto.setId(bid.getId());
    dto.setBidderName(bid.getBidderName());
    dto.setBidDate(bid.getBidDate());
    return dto;
}

    public static Bid fromBidDTOtoBid(BidDTO bidDTO) {
        Bid bid = new Bid();
        bid.setId(bidDTO.getId());
        bid.setBidderName(bidDTO.getBidderName());
        return bid;
    }
//    public static BidDTO fromBid (Bid bid) {
//        BidDTO bidDTO = new BidDTO();
//        bidDTO.setId(bid.getId());
//        bidDTO.setBidDate(bid.getBidDate());
//        bidDTO.setBidderName(bid.getBidderName());
////        bidDTO.setStartPrice(bid.getLot().getStartPrice());
////        bidDTO.setBidPrice(bid.getLot().getBidPrice());
//        bidDTO.setLotId(bid.getLot().getId());
//        return bidDTO;
//    }
//
//    public Bid toBidDTO(BidDTO bidDTO) {
//        Bid bid = new Bid();
////        bid.setLot(bidDTO.g);
//        bid.setId(bidDTO.getId());
//        bid.setBidDate(bidDTO.getBidDate());
//        bid.setBidderName(bidDTO.getBidderName());
//
//        return bid;
//    }
}
