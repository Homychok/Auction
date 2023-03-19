package com.example.auction.services;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import com.example.auction.repositories.BidRepository;
import com.example.auction.repositories.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BidService {
    private BidRepository bidRepository;
    private LotRepository lotRepository;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }
@Autowired
    public BidService(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }

    public BidDTO createNewBidder (BidDTO bidDTO) {
        Lot lot = lotRepository.findById(bidDTO.getLotId()).get();
        Bid bid = bidDTO.toBid();
        bid.setLot(lot);
        Bid createNewBidder = bidRepository.save(bid);
        return BidDTO.fromBid(createNewBidder);
    }
    public BidDTO updateInfoAboutBidder (BidDTO bidDTO) {
        Lot lot = lotRepository.findById(bidDTO.getLotId()).get();
        Bid bid = bidDTO.toBid();
        bid.setLot(lot);
        Bid updateInfoAboutBidder = bidRepository.save(bid);
        return BidDTO.fromBid(updateInfoAboutBidder);
    }
    public void deleteBidderById (Long bidderId) {
        bidRepository.deleteById(bidderId);
    }
    public BidDTO getBidderById (Long bidderId) {
       return BidDTO.fromBid(bidRepository.findById(bidderId).get());
    }
    public Collection<BidDTO> getAllBidders() {
        return bidRepository.findAll()
                .stream()
                .map(BidDTO::fromBid)
                .collect(Collectors.toList());
    }
    public Collection<BidDTO> getBidderByName (String bidderName) {
        return bidRepository.findByBidderNameContainsIgnoreCase(bidderName).stream()
                .map(BidDTO::fromBid)
                .collect(Collectors.toList());
    }
    public LotDTO getLotByBidderId(Long bidderId) {
        Lot lot = lotRepository.findById(getBidderById(bidderId).getLotId()).get();
        return LotDTO.fromLot(lot);
    }
    public Collection<BidDTO> getBidderByDate (LocalDateTime bidDate) {
        return bidRepository.findByBidDate(bidDate).stream()
                .map(BidDTO::fromBid)
                .collect(Collectors.toList());
    }
    public Long getFirstBidderByLotId (int bidderId, Long lotId) {
            return bidRepository.findByBidDateMin(bidderId, lotId);
        }
    public Long getLastBidderByLotId (int bidderId, Long lotId) {
        return bidRepository.findByBidDateMax(bidderId, lotId);
    }
    public Long getCountNumberOfBidByLotId(int bidderId, Long lotId) {
        return bidRepository.getCountNumberOfBidByLotId(bidderId, lotId);
    }
    public Long getMaxBiddersOfBidByLotId(int bidderId, Long lotId) {
        return bidRepository.getMaxBiddersOfBidByLotId(bidderId, lotId);
    }
}
