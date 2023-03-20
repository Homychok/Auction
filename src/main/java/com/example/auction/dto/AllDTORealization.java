package com.example.auction.dto;

import com.example.auction.models.Lot;
import com.example.auction.models.Bid;

public class AllDTORealization {
    public static Lot fromCreatedLotDTOToLot(CreateLotDTO createdLotDTO){
        Lot lot = new Lot();
        lot.setId(createdLotDTO.getId());
        lot.setTitle(createdLotDTO.getTitle());
        lot.setDescription(createdLotDTO.getDescription());
        lot.setStartPrice(createdLotDTO.getStartPrice());
        lot.setBidPrice(createdLotDTO.getBidPrice());
        return lot;
    }

    public static LotDTO fromLotToLotDTO(Lot lot) {
        LotDTO dto = new LotDTO();
        dto.setId(lot.getId());
        dto.setStatus(lot.getStatus());
        dto.setTitle(lot.getTitle());
        dto.setDescription(lot.getDescription());
        dto.setStartPrice(lot.getStartPrice());
        dto.setBidPrice(lot.getBidPrice());
        return dto;
    }

    public static Lot fromLotDTOToLot(LotDTO lotDTO) {
        Lot lot = new Lot();
        lot.setId(lotDTO.getId());
        lot.setStatus(lotDTO.getStatus());
        lot.setTitle(lotDTO.getTitle());
        lot.setDescription(lotDTO.getDescription());
        lot.setStartPrice(lotDTO.getStartPrice());
        lot.setBidPrice(lotDTO.getBidPrice());
        return lot;
    }
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
