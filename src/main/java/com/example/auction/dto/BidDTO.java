package com.example.auction.dto;

import com.example.auction.models.Bid;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BidDTO {
    @JsonIgnore //не выводит это поле
    private Long id;
    private String bidderName;
    @JsonIgnore//не выводит это поле
    private LocalDateTime bidDate;
    private Long lotId;
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
}
