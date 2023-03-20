package com.example.auction.dto;

import com.example.auction.enums.LotStatus;
import com.example.auction.models.Lot;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LotDTO {
    private Long id;
    private LotStatus status;
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
//    public static LotDTO fromLotToLotDTO (Lot lot) {
//        LotDTO lotDTO = new LotDTO();
//        lotDTO.setId(lot.getId());
//        lotDTO.setStatus(lot.getStatus());
//        lotDTO.setTitle(lot.getTitle());
//        lotDTO.setDescription(lot.getDescription());
//        lotDTO.setBidPrice(lot.getBidPrice());
//        lotDTO.setStartPrice(lot.getStartPrice());
//        return lotDTO;
//    }
//
//    public Lot toLotFromLotDTO(LotDTO lotDTO) {
//        Lot lot = new Lot();
//        lot.setId(lotDTO.getId());
//        lot.setStatus(lotDTO.getStatus());
//        lot.setTitle(lotDTO.getTitle());
//        lot.setDescription(lotDTO.getDescription());
//        lot.setStartPrice(lotDTO.getStartPrice());
//        lot.setBidPrice(lotDTO.getBidPrice());
//        return lot;
//    }
}
