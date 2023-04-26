package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    File addFile(MultipartFile file) throws IOException;
    File getFile(String id) throws IOException;
}
