package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReactionService {
    List<Reaction> getReactions();
    long getReaction(String id, String type);
}
