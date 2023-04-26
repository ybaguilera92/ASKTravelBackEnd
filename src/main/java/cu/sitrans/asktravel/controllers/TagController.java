package cu.sitrans.asktravel.controllers;

import cu.sitrans.asktravel.payload.request.TagRequest;
import cu.sitrans.asktravel.payload.response.ResponseHandler;
import cu.sitrans.asktravel.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody TagRequest tagRequest){
        return ResponseHandler.generateResponse("Registro realizado satisfactoriamente", HttpStatus.OK, tagService.save(tagRequest.getTitle(), tagRequest.getColor()));
    }

    @GetMapping("/tags")
    public ResponseEntity<?> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }
}

