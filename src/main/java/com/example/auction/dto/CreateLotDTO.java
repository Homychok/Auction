package com.example.auction.dto;

import com.example.auction.models.Lot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateLotDTO {
    @JsonIgnore
    private Long id;
    private String title;
    private String description;
    private Long startPrice;
    private Long bidPrice;
    public static Lot fromCreatedLotDTOToLot(CreateLotDTO createdLotDTO){
        Lot lot = new Lot();
        lot.setId(createdLotDTO.getId());
        lot.setTitle(createdLotDTO.getTitle());
        lot.setDescription(createdLotDTO.getDescription());
        lot.setStartPrice(createdLotDTO.getStartPrice());
        lot.setBidPrice(createdLotDTO.getBidPrice());
        return lot;
    }
}
