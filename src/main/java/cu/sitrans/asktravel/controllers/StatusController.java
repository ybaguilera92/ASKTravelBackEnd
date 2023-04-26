package cu.sitrans.asktravel.controllers;

import cu.sitrans.asktravel.models.Status;
import cu.sitrans.asktravel.payload.request.StatusDTO;
import cu.sitrans.asktravel.payload.response.ResponseHandler;
import cu.sitrans.asktravel.service.StatusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    StatusService statusService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody StatusDTO statusDTO){
        return ResponseHandler.generateResponse("Registro realizado satisfactoriamente", HttpStatus.OK, statusService.save(modelMapper.map(statusDTO, Status.class)));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        return ResponseEntity.ok(statusService.getStatus());
    }
}

