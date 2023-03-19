package com.example.auction.controllers;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.models.Lot;
import com.example.auction.models.LotsPic;
import com.example.auction.services.LotService;
import com.example.auction.services.LotsPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("lots")
public class LotController {
    private LotService lotService;
    private LotsPicService lotsPicService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @Autowired
    public LotController(LotService lotService, LotsPicService lotsPicService) {
        this.lotService = lotService;
        this.lotsPicService = lotsPicService;
    }

    @GetMapping("{lotId}") // GET http://localhost:8080/lots/3
    public ResponseEntity<LotDTO> getLotById(@PathVariable Long lotId) {
        LotDTO lot = lotService.getLotById(lotId);
        if (lotId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(lot);
    }

    @GetMapping("{lotTitle}") // GET http://localhost:8080/lots/str
    public ResponseEntity<LotDTO> getLotByTitle(@RequestParam String lotTitle) {
        LotDTO lot = lotService.getLotByTitle(lotTitle);
        if (lotTitle.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(lot);
    }

    @GetMapping
    public ResponseEntity<List<Lot>> getAllLots(@RequestParam("page") Integer pageNumber, @RequestParam("size") Integer pageSize) {
        List<Lot> lots = lotService.getAllLots(pageNumber, pageSize);
        return ResponseEntity.ok(lots);
    }

    @GetMapping// GET http://localhost:8080/lots
    public ResponseEntity<Collection<LotDTO>> getLotsByStatus(@RequestParam(required = false) String lotStatus) {
        if (lotStatus.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(lotService.getLotsByStatus(lotStatus));
    }

    @GetMapping("{lotId}/bidders") // GET http://localhost:8080/lots/3/lots
    public ResponseEntity<Collection<BidDTO>> getBiddersByLotId(@PathVariable Long lotId) {
        return ResponseEntity.ok(lotService.getBiddersByLotId(lotId));
    }

    @GetMapping(value = "/{lotId}/picture/preview")
    public ResponseEntity<byte[]> downloadPicture(@PathVariable Long lotId) {
        LotsPic lotsPic = lotsPicService.getLotsPic(lotId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(lotsPic.getMediaType()));
        headers.setContentLength(lotsPic.getPreview().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(lotsPic.getPreview());
    }

    @PostMapping// POST http://localhost:8080/lots
    public ResponseEntity<LotDTO> createNewLot(@RequestBody LotDTO lotDTO) {
        LotDTO createNewLot = lotService.createNewLot(lotDTO);
        return ResponseEntity.ok(createNewLot);
    }

    @PutMapping// PUT http://localhost:8080/lots
    public ResponseEntity<LotDTO> updateInfoAboutLot(@RequestBody LotDTO lotDTO) {
        LotDTO updateInfoAboutLot = lotService.updateInfoAboutLot(lotDTO);
        if (updateInfoAboutLot == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateInfoAboutLot);
    }

    @DeleteMapping("{lotId}") // DELETE http://localhost:8080/lots/3
    public ResponseEntity<Lot> deleteLotById(@PathVariable Long lotId) {
        lotService.deleteLotById(lotId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{lotId}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAvatar(@PathVariable Long pictureId,
                                               @RequestParam MultipartFile LotsPicDTO) throws IOException {
        if (LotsPicDTO.getSize() >= 1024 * 300) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        lotsPicService.uplodePicture(pictureId, LotsPicDTO);
        return ResponseEntity.ok().build();
    }
}


