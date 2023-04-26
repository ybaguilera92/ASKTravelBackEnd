package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.Status;

import java.util.List;

public interface StatusService {
    Status save(Status status);
    List<Status> getStatus();
}
