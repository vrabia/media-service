package app.vrabia.mediaservice.controller;

import app.vrabia.mediaservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/communities/{imageName}")
    public ResponseEntity<byte[]> getCommunityImage(@PathVariable("imageName") String imageName) throws IOException {
        log.info("getCommunityImage called");
        byte[] image = imageService.getCommunityImage(imageName);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(image.length)
                .body(image);
    }

    @PostMapping("/communities")
    public ResponseEntity<Void> uploadCommunityImage(@RequestParam("file") MultipartFile file) {
        log.info("uploadCommunityImage called");

        imageService.saveCommunityImage(file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
