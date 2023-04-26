package cu.sitrans.asktravel.service.impl;

import cu.sitrans.asktravel.models.Status;
import cu.sitrans.asktravel.models.Tag;
import cu.sitrans.asktravel.repositories.StatusRepository;
import cu.sitrans.asktravel.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepository statusRepository;

    @Override
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public List<Status> getStatus() {
        return statusRepository.findAll();
    }
}
