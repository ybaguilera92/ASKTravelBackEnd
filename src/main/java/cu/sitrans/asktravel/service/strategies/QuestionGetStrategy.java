package cu.sitrans.asktravel.service.strategies;

import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.repositories.QuestionRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class QuestionGetStrategy implements BaseGetStrategy<Question> {

    @Autowired
    protected QuestionRepository questionRepository;
    protected Pageable pageable;

    public Map<String, Object> formatResultList(Page page){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ArrayList<Question> questionLists = Lists.newArrayList(page);
        if (authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
            for (Question question : questionLists) {
                question.setTags(question.getTags().stream().filter(tag -> tag.getStatus().equals("A")).collect(Collectors.toList()));
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("questions", questionLists);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return response;
    }

}
