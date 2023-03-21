package com.example.auction.dto;

import com.example.auction.models.Lot;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.auction.enums.LotStatus;

@Data
@NoArgsConstructor
public class FullLotDTO {
    private BidDTOForFullLotDTO lastBid;
    private Long id;
    private LotStatus status;
    private String title;
    private String description;
    private Long currentPrice;
    private Long startPrice;
    private Long bidPrice;
public static FullLotDTO fromLotDTOToFullLotDTO (LotDTO lotDTO) {
    FullLotDTO fullDTO = new FullLotDTO();
    fullDTO.setId(lotDTO.getId());
    fullDTO.setStatus(lotDTO.getStatus());
    fullDTO.setTitle(lotDTO.getTitle());
    fullDTO.setDescription(lotDTO.getDescription());
    fullDTO.setStartPrice(lotDTO.getStartPrice());
    fullDTO.setBidPrice(lotDTO.getBidPrice());
    return fullDTO;
}

    public static FullLotDTO fromLotToFullLotDTO (Lot lot) {
        FullLotDTO dto = new FullLotDTO();
        dto.setId(lot.getId());
        dto.setStatus(lot.getStatus());
        dto.setTitle(lot.getTitle());
        dto.setDescription(lot.getDescription());
        dto.setStartPrice(lot.getStartPrice());
        dto.setBidPrice(lot.getBidPrice());
        return dto;
    }
}
