package com.example.auction.services;

import com.example.auction.dto.CreateLotDTO;
import com.example.auction.models.Lot;
import com.example.auction.repositories.LotRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateLotService {
    private LotRepository lotRepository;

    public CreateLotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }
    public CreateLotDTO createNewCreateLotDTO (CreateLotDTO createLotDTO) {
        Lot lot = createLotDTO.toCreateLotDTO();
        Lot createNewCreateLotDTO = lotRepository.save(lot);
        return CreateLotDTO.fromCreateLotDTO(createNewCreateLotDTO);
    }
}
