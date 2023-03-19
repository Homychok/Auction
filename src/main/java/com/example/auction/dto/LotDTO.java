package com.example.auction.dto;

import com.example.auction.models.Lot;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@NoArgsConstructor
public class LotDTO {
    private Long lotId;
    private Lot.LotStatus lotStatus;
    private String lotTitle;
    private String lotDescription;
    private int lotStartPrice;
    private int lotBidPrice;
    public static LotDTO fromLot (Lot lot) {
        LotDTO lotDTO = new LotDTO();
        lotDTO.setLotId(lot.getLotId());
        lotDTO.setLotStatus(lot.getLotStatus());
        lotDTO.setLotTitle(lot.getLotTitle());
        lotDTO.setLotDescription(lot.getLotDescription());
        lotDTO.setLotBidPrice(lot.getLotBidPrice());
        lotDTO.setLotStartPrice(lot.getLotStartPrice());
        return lotDTO;
    }

    public Lot toLot() {
        Lot lot = new Lot();
        lot.setLotId(this.getLotId());
        lot.setLotStatus(this.getLotStatus());
        lot.setLotTitle(this.getLotTitle());
        lot.setLotDescription(this.getLotDescription());
        lot.setLotStartPrice(this.getLotStartPrice());
        lot.setLotBidPrice(this.getLotBidPrice());
        return lot;
    }
}
