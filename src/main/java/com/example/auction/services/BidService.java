package com.example.auction.services;
import com.example.auction.dto.LotDTO;
import com.example.auction.dto.BidDTO;
import com.example.auction.dto.BidDTOForFullLotDTO;
import com.example.auction.models.Bid;
import com.example.auction.pojection.LotProjection;
import com.example.auction.repositories.BidRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
public class BidService {
    private BidRepository bidRepository;
//    private LotService lotService;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }
////@Autowired
//    public BidService(BidRepository bidRepository, LotService lotService) {
//        this.bidRepository = bidRepository;
//        this.lotService = lotService;
////    }
//    public BidDTO createBid (BidDTO bidDTO) {
//        Bid bid = BidDTO.toBid(bidDTO);
//        bid.setBidderName(bid.getBidderName());
//        Bid newBid = bidRepository.save(bid);
//        return BidDTO.fromBid(newBid);
//    }
    public BidDTOForFullLotDTO createNewBidder (BidDTOForFullLotDTO bidDTOForFullLotDTO, LotDTO lotDTO) {
        Bid bid = bidDTOForFullLotDTO.toBid();
        bid.setLot(lotDTO.toLot());
        bid.setBidDate(LocalDateTime.now());
        return BidDTOForFullLotDTO.fromBid(bidRepository.save(bid));
    }
    public LotProjection getFirstBidderByLotId (Long lotId) {
        return bidRepository.findByBidDateMin(lotId);
    }
    public Bid getLastBidderByLotId (Long lotId) {
        return bidRepository.findByBidDateMax(lotId);
    }
    public LotProjection getMaxBiddersOfBidByLotId(Long lotId) {
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidderName(bidRepository.findByBidDateMax(lotId).getBidderName());
        bidDTO.setBidDate(bidRepository.findByBidDateMax(lotId).getBidDate());
        return bidRepository.getMaxBidders(lotId);
    }
    public Long countTotalPrice(Long lotId) {
        return bidRepository.getCountNumberOfBidByLotId(lotId);
    }
    public BidDTOForFullLotDTO findLastBid(Long id) {
        return BidDTOForFullLotDTO.fromBid(bidRepository.findByBidDateMax(id));
    }
}
