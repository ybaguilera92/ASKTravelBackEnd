package cu.sitrans.asktravel.service.impl;


import cu.sitrans.asktravel.models.File;
import cu.sitrans.asktravel.repositories.FileRepository;
import cu.sitrans.asktravel.service.FileService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public File addFile(MultipartFile file) throws IOException {
        File fileImg = new File(file.getOriginalFilename());
        fileImg.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        return fileRepository.insert(fileImg);
    }

    @Override
    public File getFile(String id) {
        return fileRepository.findById(id).get();
    }
}
