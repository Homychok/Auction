package com.example.auction.dto;

import com.example.auction.models.Lot;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullLotDTO {
    private int lotLastBid;
    private int lotId;
    private String lotStatus;
    private String lotTitle;
    private String lotDescription;
    private int lotCurrentPrice;
    private int lotStartPrice;
    private int lotBidPrice;
    public static FullLotDTO fromLot (Lot lot) {
        FullLotDTO fullLotDTO = new FullLotDTO();
        fullLotDTO.setLotId(lot.getLotId());
        fullLotDTO.setLotStatus(lot.getLotStatus());
        fullLotDTO.setLotTitle(lot.getLotTitle());
        fullLotDTO.setLotDescription(lot.getLotDescription());
        fullLotDTO.setLotBidPrice(lot.getLotBidPrice());
        fullLotDTO.setLotStartPrice(lot.getLotStartPrice());
        fullLotDTO.setLotCurrentPrice(lot.getLotCurrentPrice());
        fullLotDTO.setLotLastBid(lot.getLotLastBid());
        return fullLotDTO;
    }

    public Lot toLot() {
        Lot lot = new Lot();
        lot.setLotId(this.getLotId());
        lot.setLotStatus(this.getLotStatus());
        lot.setLotTitle(this.getLotTitle());
        lot.setLotDescription(this.getLotDescription());
        lot.setLotStartPrice(this.getLotStartPrice());
        lot.setLotBidPrice(this.getLotBidPrice());
        lot.setLotCurrentPrice(this.getLotCurrentPrice());
        lot.setLotLastBid(this.getLotLastBid());
        return lot;
    }
}
