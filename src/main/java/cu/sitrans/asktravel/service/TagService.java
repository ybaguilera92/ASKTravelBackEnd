package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.Tag;

import java.util.List;

public interface TagService {
    Tag save(String tagName, String tagColor);
    List<Tag> getTags();
}
