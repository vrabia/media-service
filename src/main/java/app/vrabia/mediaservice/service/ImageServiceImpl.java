package app.vrabia.mediaservice.service;

import app.vrabia.vrcommon.exception.ErrorCodes;
import app.vrabia.vrcommon.exception.VrabiaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private static final String IMAGE_FOLDER = "/images/communities/";
    @Override
    public byte[] getCommunityImage(String name) throws IOException {
        Path imagePath = Paths.get(IMAGE_FOLDER + name);

        if (!Files.exists(imagePath)) {
            throw new VrabiaException(ErrorCodes.FILE_NOT_FOUND);
        }

        return Files.readAllBytes(imagePath);
    }

    @Override
    public void saveCommunityImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File directory = new File(IMAGE_FOLDER);

        if (!directory.exists()) {
            directory.mkdir();
        }

        File targetFile = new File(IMAGE_FOLDER + fileName);

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            log.error("Error saving image", e);
            throw new VrabiaException(ErrorCodes.INTERNAL_SERVER_ERROR);
        }
    }
}
