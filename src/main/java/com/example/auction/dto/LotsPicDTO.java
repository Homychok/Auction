package com.example.auction.dto;

import com.example.auction.models.LotsPic;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LotsPicDTO {
    private Long pictureId;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] preview;
    private Long lotId;

    public static LotsPicDTO fromLotsPic (LotsPic lotsPic) {
        LotsPicDTO lotsPicDTO = new LotsPicDTO();
        lotsPicDTO.setPictureId(lotsPic.getPictureId());
        lotsPicDTO.setFilePath(lotsPic.getFilePath());
        lotsPicDTO.setFileSize(lotsPic.getFileSize());
        lotsPicDTO.setMediaType(lotsPic.getMediaType());
        lotsPicDTO.setPreview(lotsPic.getPreview());
        lotsPicDTO.setLotId(lotsPic.getLot().getLotId());
        return lotsPicDTO;
    }

    public LotsPic toLotsPic() {
        LotsPic lotsPic = new LotsPic();
        lotsPic.setPictureId(this.getPictureId());
        lotsPic.setFilePath(this.getFilePath());
        lotsPic.setFileSize(this.getFileSize());
        lotsPic.setMediaType(this.getMediaType());
        lotsPic.setPreview(this.getPreview());
        return lotsPic;
    }
}
