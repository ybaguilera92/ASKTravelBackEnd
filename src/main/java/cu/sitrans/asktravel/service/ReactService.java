package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.Post;
import cu.sitrans.asktravel.models.Reaction;

public interface ReactService <T extends Post>  {
    public T react (String id, Reaction reaction);
}
