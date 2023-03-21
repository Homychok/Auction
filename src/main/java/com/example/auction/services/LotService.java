package com.example.auction.services;

import com.example.auction.dto.*;
import com.example.auction.enums.LotStatus;
import com.example.auction.models.Lot;
import com.example.auction.repositories.BidRepository;
import com.example.auction.repositories.LotRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LotService {
    private LotRepository lotRepository;
    private BidRepository bidRepository;
    private BidDTOForFullLotDTO bidDTOForFullLotDTO;

    public LotService(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }
    public LotDTO createNewLot (CreateLotDTO createLotDTO) {
        Lot lot = CreateLotDTO.fromCreatedLotDTOToLot(createLotDTO);
        lot.setStatus(LotStatus.CREATED);
        Lot createNewLot = lotRepository.save(lot);
        return LotDTO.fromLotToLotDTO(createNewLot);
    }

    public List<LotDTO> getAllLotsByStatusOnPage (LotStatus lotStatus, Integer pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, 10);
        return lotRepository.findAllByStatus(lotStatus, pageRequest)
                .stream()
                .map(LotDTO::fromLotToLotDTO)
                .collect(Collectors.toList());
    }
    public Collection<FullLotDTO> getAllFullLots() {
        return lotRepository.findAll()
                .stream()
                .map(FullLotDTO::fromLotToFullLotDTO)
                .collect(Collectors.toList());
    }
    private Long totalPrice(Long lotId, Long bidPrice, Long startPrice) {
        return (bidRepository.getCountNumberOfBidByLotId(lotId) * bidPrice + startPrice);
    }
    public FullLotDTO getFullLotById (Long id) {
        FullLotDTO fullLotDTO = FullLotDTO.fromLotDTOToFullLotDTO(getLotById(id));
        Long currentPrice = totalPrice(id, fullLotDTO.getBidPrice(), fullLotDTO.getStartPrice());
        fullLotDTO.setCurrentPrice(currentPrice);
        fullLotDTO.setLastBid(getLastBidForLot(bidDTOForFullLotDTO.getLotId()));
        return fullLotDTO;
    }
    public BidDTOForFullLotDTO getLastBidForLot (Long lotId) {
        BidDTOForFullLotDTO bidDTOForFullLotDTO = new BidDTOForFullLotDTO();
        bidDTOForFullLotDTO.setBidderName(bidRepository.findByBidDateMax(lotId).getBidderName());
        bidDTOForFullLotDTO.setBidDate(bidRepository.findByBidDateMax(lotId).getBidDate());
        return bidDTOForFullLotDTO;
    }
    public LotDTO getLotById (Long id) {
        return LotDTO.fromLotToLotDTO(lotRepository.findById(id).get());
    }
    public void updateStatusToStarted(Long id) {
        Lot lot = lotRepository.findById(id).get();
        lot.setStatus(LotStatus.STARTED);
        lotRepository.save(lot);
    }
    public void updateStatusToStopped(Long id) {
        Lot lot = lotRepository.findById(id).get();
        lot.setStatus(LotStatus.STOPPED);
        lotRepository.save(lot);
    }
    public boolean checkMistakeInCreatingLot(CreateLotDTO createLotDTO) {
        if(createLotDTO.getTitle() == null || createLotDTO.getTitle().isBlank() ||
                createLotDTO.getDescription() == null || createLotDTO.getDescription().isBlank() ||
        createLotDTO.getStartPrice() == null || createLotDTO.getBidPrice() == null){
            return false;
        } else {
            return true;
        }
    }
}
