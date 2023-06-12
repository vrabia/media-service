package app.vrabia.mediaservice.controller;

import app.vrabia.mediaservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    @GetMapping("/communities/names")
    public ResponseEntity<List<String>> getCommunityImageNames() {
        log.info("getCommunityImageNames called");
        List<String> imageNames = imageService.getCommunityImageNames();
        return ResponseEntity
                .ok()
                .body(imageNames);
    }

    @PostMapping("/store-html")
    public String storeHtml(@RequestParam("filename") String filename, @RequestBody String htmlContent) {
        try {
            // Create a new file

            File directory = new File("/images/html/");
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File("/images/html/" + filename);

            // Write the HTML content to the file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(htmlContent);
            fileWriter.close();

            return "HTML file stored successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to store HTML file.";
        }
    }

    @GetMapping("/html/{fileName}")
    public ResponseEntity<byte[]> getHtml(@PathVariable String fileName) throws IOException {
        // Read the HTML file into a byte array
        Path htmlPath = Paths.get("/images/html/", fileName);
        byte[] htmlBytes = Files.readAllBytes(htmlPath);

        // Set the content type as text/html
        MediaType contentType = MediaType.TEXT_HTML;
        log.info("getHtml called");

        // Return the HTML bytes with the appropriate content type
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(htmlBytes);
    }
}
