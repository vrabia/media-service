package app.vrabia.mediaservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    byte[] getCommunityImage(String name) throws IOException;

    void saveCommunityImage(MultipartFile file);
}
