package cu.sitrans.asktravel.service.impl;

import cu.sitrans.asktravel.models.Tag;
import cu.sitrans.asktravel.repositories.TagRepository;
import cu.sitrans.asktravel.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag save(String tagTitle, String tagColor) {
        Tag tag = new Tag();
        tag.setTitle(tagTitle);
        tag.setColor(tagColor);
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }
}
