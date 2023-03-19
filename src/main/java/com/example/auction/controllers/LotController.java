package com.example.auction.controllers;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.FullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.models.Lot;
import com.example.auction.models.LotsPic;
import com.example.auction.services.CreateLotService;
import com.example.auction.services.FullLotService;
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

@RestController
@RequestMapping("lots")
public class LotController {
    private LotService lotService;
    private FullLotService fullLotService;
    private CreateLotService createLotService;
    private LotsPicService lotsPicService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }
@Autowired
    public LotController(LotService lotService, FullLotService fullLotService, CreateLotService createLotService, LotsPicService lotsPicService) {
        this.lotService = lotService;
        this.fullLotService = fullLotService;
        this.createLotService = createLotService;
        this.lotsPicService = lotsPicService;
    }

/*
Получить полную информацию о лоте
 */
    @GetMapping("/lot/{lotId}") // GET http://localhost:8080/lots/3
    public ResponseEntity<FullLotDTO> getFullLotById(@PathVariable Long lotId) {
        FullLotDTO lot = fullLotService.getFullLotById(lotId);
        if (lotId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(lot);
    }
//
//    @GetMapping("{lotTitle}") // GET http://localhost:8080/lots/str
//    public ResponseEntity<LotDTO> getLotByTitle(@RequestParam String lotTitle) {
//        LotDTO lot = lotService.getLotByTitle(lotTitle);
//        if (lotTitle.isBlank()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(lot);
//    }
/*
Получить все лоты, основываясь на фильтре статуса и номере страницы
 */
    @GetMapping
    public ResponseEntity<Collection<LotDTO>> getAllLots(@RequestParam("page") Integer pageNumber,
                                                         @RequestParam("size") Integer pageSize,
                                                         @RequestParam(required = false) String lotStatus) {
        if (!lotStatus.isBlank()) {
            return ResponseEntity.ok(lotService.getLotsByStatus(lotStatus));
        }
        if (pageSize <= 0 || pageSize > 50) {
            return ResponseEntity.ok(lotService.getAllLots(pageNumber, 50));
        }
        return ResponseEntity.ok(lotService.getAllLots(pageNumber, pageSize));
    }
//Ненужные методы:
//    @GetMapping// GET http://localhost:8080/lots
//    public ResponseEntity<Collection<LotDTO>> getLotsByStatus(@RequestParam(required = false) String lotStatus) {
//        if (lotStatus.isBlank()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(lotService.getLotsByStatus(lotStatus));
//    }
//
//    @GetMapping("{lotId}/bidders") // GET http://localhost:8080/lots/3/lots
//    public ResponseEntity<Collection<BidDTO>> getBiddersByLotId(@PathVariable Long lotId) {
//        return ResponseEntity.ok(lotService.getBiddersByLotId(lotId));
//    }
//
//    @GetMapping(value = "/{lotId}/picture/preview")
//    public ResponseEntity<byte[]> downloadPicture(@PathVariable Long lotId) {
//        LotsPic lotsPic = lotsPicService.getLotsPic(lotId);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(lotsPic.getMediaType()));
//        headers.setContentLength(lotsPic.getPreview().length);
//        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(lotsPic.getPreview());
//    }
    /*
Создает новый лот
     */
    @PostMapping// POST http://localhost:8080/lots
    public ResponseEntity<LotDTO> createNewLot(@RequestBody LotDTO lotDTO) {
        LotDTO createNewLot = lotService.createNewLot(lotDTO);
        if (createNewLot == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(createNewLot);
    }
/*
Начать торги по лоту
 */
@PutMapping("{lotStatus}/start")// PUT http://localhost:8080/lots
public ResponseEntity<LotDTO> updateStatusAuctionStarted(@RequestBody Long lotId) {
    LotDTO updateInfoAboutLot = lotService.updateStatus(lotId, "STARTED");
    if (updateInfoAboutLot == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    if (lotId == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.ok(updateInfoAboutLot);
}
    /*
    Остановить торги по лоту
     */
    @PutMapping("{lotStatus}/stop")// PUT http://localhost:8080/lots
    public ResponseEntity<LotDTO> updateStatusAuctionStop(@RequestBody Long lotId) {
        LotDTO updateInfoAboutLot = lotService.updateStatus(lotId, "STOPPED");
        if (updateInfoAboutLot == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (lotId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateInfoAboutLot);
}
//    @PutMapping("{lotId}")// PUT http://localhost:8080/lots
//    public ResponseEntity<LotDTO> updateStatusAboutLot(@RequestBody LotDTO lotDTO) {
//        LotDTO updateInfoAboutLot = lotService.updateInfoAboutLot(lotDTO);
//        if (updateInfoAboutLot == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(updateInfoAboutLot);
//    }
/*
убрать лот с аукциона
 */
    @DeleteMapping("{lotId}") // DELETE http://localhost:8080/lots/3
    public ResponseEntity<Lot> deleteLotById(@PathVariable Long lotId) {
        lotService.deleteLotById(lotId);
        return ResponseEntity.ok().build();
    }

//    @PostMapping(value = "/{lotId}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> updateAvatar(@PathVariable Long pictureId,
//                                               @RequestParam MultipartFile LotsPicDTO) throws IOException {
//        if (LotsPicDTO.getSize() >= 1024 * 300) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        lotsPicService.uplodePicture(pictureId, LotsPicDTO);
//        return ResponseEntity.ok().build();
//    }

}


