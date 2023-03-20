package com.example.auction.services;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.FullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import com.example.auction.repositories.BidRepository;
import com.example.auction.repositories.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LotService {
    private LotRepository lotRepository;
    private BidRepository bidRepository;
    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Autowired
    public LotService(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }
    public LotDTO createNewLot (LotDTO lotDTO) {
        Lot lot = lotDTO.toLot();
        Lot createNewLot = lotRepository.save(lot);
        return LotDTO.fromLot(createNewLot);
    }
    public LotDTO updateStatus (Long lotId, String lotStatus) {
//        Lot lot = lotDTO.toLot();
        Lot updateInfoAboutLot = lotRepository.updateLotStatus(lotId, lotStatus);
        return LotDTO.fromLot(updateInfoAboutLot);
    }
//    public LotDTO updateStatusStopped (Long lotId, String lotStatus) {
//        Lot updateInfoAboutLot = lotRepository.updateLotStatus(lotId, lotStatus);
//        return LotDTO.fromLot(updateInfoAboutLot);
//    }
    public String findStatus (Long lotId) {
        String lotStatus = String.valueOf(lotRepository.findLotStatusByLotId(lotId));
        return lotStatus;
    }
//    public LotDTO updateInfoAboutLot (LotDTO lotDTO) {
//        Lot lot = lotDTO.toLot();
//        Lot updateInfoAboutLot = lotRepository.save(lot);
//        return LotDTO.fromLot(updateInfoAboutLot);
//    }
    public void deleteLotById (Long lotId) {
        lotRepository.deleteById(lotId);
    }
    public List<LotDTO> getAllLots (Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return lotRepository.findAll(pageRequest).getContent()
                .stream()
                .map(LotDTO::fromLot)
                .collect(Collectors.toList());
    }

    public List<Lot> getAllLotsByPage (Integer pageNumber, Integer pageSize, String lotStatus) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return lotRepository.findAll(pageRequest).getContent();
    }

    public LotDTO getLotById (Long lotId) {
        return LotDTO.fromLot(lotRepository.findById(lotId).get());
    }
//    public Collection<LotDTO> getAllLots() {
//        return lotRepository.findAll()
//                .stream()
//                .map(LotDTO::fromLot)
//                .collect(Collectors.toList());
//    }
    public Collection<FullLotDTO> getAllFullLots() {
        return lotRepository.findAll()
                .stream()
                .map(FullLotDTO::fromFullLot)
                .collect(Collectors.toList());
    }
    public Collection<BidDTO> getBiddersByLotId(Long lotId) {
        List<Bid> bids = lotRepository.findById(lotId).get().getBids();
        List<BidDTO> bidDTOs = new ArrayList<>();
        for(Bid bid : bids) {
            BidDTO bidDTO = BidDTO.fromBid(bid);
            bidDTOs.add(bidDTO);
        }
        return bidDTOs;
    }
    public Collection<LotDTO> getLotsByStatus(String lotStatus){
        return lotRepository.findByLotStatus(lotStatus)
                .stream()
                .map(LotDTO::fromLot)
                .collect(Collectors.toList()); }
    public LotDTO getLotByTitle (String lotTitle) {
        return LotDTO.fromLot(lotRepository.findByLotTitleContainsIgnoreCase(lotTitle));
    }
//    public Collection<FullLotDTO> getAllLotsForExport(Long bidderId, Long lotId) {
//        return lotRepository.findAll().stream()
//                .map(FullLotDTO::fromLot)
//                .peek(lot -> (lot).setLotCurrentPrice(sumCurrentPrice(lot.getLotId(), lot.getLotLastBid(), lot.getLotStartPrice())))
//                .peek(lot -> (lot).setLotBidPrice(findInfoABoutLastBid(bidderId,lotId)
//                .getLotBidPrice();
//    }
    private Integer sumCurrentPrice(Long lotId, Integer lotBidPrice, Integer lotStartPrice) {
        return (int) (bidRepository.getBidderCount(lotId) * lotBidPrice + lotStartPrice);
    }
    public FullLotDTO createNewFullLot (FullLotDTO fullLotDTO) {
        Lot lot = fullLotDTO.toFullLot();
        Lot createNewFullLot = lotRepository.save(lot);
        return FullLotDTO.fromFullLot(createNewFullLot);
    }
    public FullLotDTO getFullLotById (Long lotId) {
        return FullLotDTO.fromFullLot(lotRepository.findById(lotId).get());
    }
}
