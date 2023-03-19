package com.example.auction.services;

import com.example.auction.dto.LotsPicDTO;
import com.example.auction.models.Lot;
import com.example.auction.models.LotsPic;
import com.example.auction.repositories.LotsPicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class LotsPicService {
    @Value("${auction.lotsPic.folder}")
    private String lotsPicDir;
    private LotService lotService;
    private LotsPicRepository lotsPicRepository;

    public LotsPicService(LotService lotService, LotsPicRepository lotsPicRepository) {
        this.lotService = lotService;
        this.lotsPicRepository = lotsPicRepository;
    }

    public void uplodePicture(Long lotId, MultipartFile file) throws IOException {
        Lot lot = lotService.getLotById(lotId).toLot();
        Path filePath = Path.of(lotsPicDir, lotId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(is, 1024);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(os, 1024)
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }

        LotsPicDTO lotsPicDTO = LotsPicDTO.fromLotsPic(getLotsPic(lotId));

        lotsPicDTO.setFilePath(filePath.toString());
        lotsPicDTO.setFileSize(file.getSize());
        lotsPicDTO.setMediaType(file.getContentType());
        lotsPicDTO.setPreview(file.getBytes());
        lotsPicDTO.setLotId(lot.getLotId());

        LotsPic lotsPic = lotsPicDTO.toLotsPic();
        lotsPic.setLot(lot);
        lotsPicRepository.save(lotsPic);
    }

    public LotsPic getLotsPic(Long lotId) {
        return lotsPicRepository.findLotsPicByLotId(lotId).orElse(new LotsPic());
    }

    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
