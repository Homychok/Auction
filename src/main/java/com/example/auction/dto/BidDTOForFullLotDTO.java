package com.example.auction.dto;

import com.example.auction.models.Bid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class BidDTOForFullLotDTO {
    private Long id;
    private String bidderName;
    @JsonIgnore
    private LocalDateTime bidDate;
    private Long lotId;
    public static BidDTOForFullLotDTO fromBidToBidDToForFullLotDTO(Bid bid) {
        BidDTOForFullLotDTO bidDTOForFullLotDTO = new BidDTOForFullLotDTO();
        bidDTOForFullLotDTO.setId(bid.getId());
        bidDTOForFullLotDTO.setBidderName(bid.getBidderName());
        bidDTOForFullLotDTO.setBidDate(bid.getBidDate());
        bidDTOForFullLotDTO.setLotId(bidDTOForFullLotDTO.getLotId());
        return bidDTOForFullLotDTO;
    }

    public static Bid fromBidDToForFullLotDTOtoBid(BidDTOForFullLotDTO bidDTOForFullLotDTO) {
        Bid bid = new Bid();
        bid.setId(bidDTOForFullLotDTO.getId());
        bid.setBidderName(bidDTOForFullLotDTO.getBidderName());
        bid.setBidDate(LocalDateTime.now());
        return bid;
    }
}
