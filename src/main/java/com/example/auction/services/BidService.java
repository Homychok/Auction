package com.example.auction.services;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.BidDTOForFullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import com.example.auction.repositories.BidRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
public class BidService {
    private BidRepository bidRepository;
    private LotService lotService;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }
@Autowired
    public BidService(BidRepository bidRepository, LotService lotService) {
        this.bidRepository = bidRepository;
        this.lotService = lotService;
    }
    public BidDTO createBid (BidDTO bidDTO) {
        Bid bid = BidDTO.fromBidDTOtoBid(bidDTO);
        bid.setBidderName(bid.getBidderName());
        Bid newBid = bidRepository.save(bid);
        return BidDTO.fromBidToBidDTO(newBid);
    }
    public BidDTOForFullLotDTO createNewBidder (BidDTOForFullLotDTO bidDToForFullLotDTO) {
        Lot lot = LotDTO.fromLotDTOToLot(lotService.getLotById(bidDToForFullLotDTO.getLotId()));
        Bid bid = BidDTOForFullLotDTO.fromBidDToForFullLotDTOtoBid(bidDToForFullLotDTO);
        bid.setLot(lot);
        bid.setBidDate(LocalDateTime.now());
        Bid createNewBidder = bidRepository.save(bid);
        return BidDTOForFullLotDTO.fromBidToBidDToForFullLotDTO(createNewBidder);
    }
    public BidDTOForFullLotDTO getFirstBidderByLotId (Long lotId) {
        return bidRepository.findByBidDateMin(lotId);
    }
    public BidDTOForFullLotDTO getMaxBiddersOfBidByLotId(Long lotId) {
        return bidRepository.getMaxBidders(lotId);
    }
}
