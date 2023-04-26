package cu.sitrans.asktravel.service.impl;

import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.models.Post;
import cu.sitrans.asktravel.models.Reaction;
import cu.sitrans.asktravel.repositories.ReactionRepository;
import cu.sitrans.asktravel.repositories.generic.BaseRepository;
import cu.sitrans.asktravel.service.impl.security.services.UserDetailsImpl;
import cu.sitrans.asktravel.service.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ReactServiceImpl <T extends Post> implements ReactService<T> {

    protected BaseRepository<T> baseRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    ReactServiceImpl(BaseRepository<T> baseRepository){
        this.baseRepository = baseRepository;
    }

     @Override
     @Transactional
     public T react(String id, Reaction reaction){

        T t = baseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Post.class, id));

         AtomicBoolean create = new AtomicBoolean(true);
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         if (authentication == null || !authentication.isAuthenticated()) {
             return null;
         }

         UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

         if (t.getReactions().isEmpty()) {
             t.setReactions(Arrays.asList(reactionRepository.save(reaction)));
         } else if (t.getReactions().stream().noneMatch(react -> react.getUser().getId().equals(user.getId()))) {
             Reaction react = reactionRepository.save(reaction);
             t.getReactions().add(react);
             t.setReactions(t.getReactions());
         } else {
             for (Reaction react : t.getReactions()) {
                 if (react.getUser().getId().equals(user.getId())) {
                     if (react.getReaction() != reaction.getReaction()) {
                         react.setReaction(reaction.getReaction());
                         reactionRepository.save(react);
                         create.set(false);
                     } else {
                         throw new IllegalArgumentException("Lo sentimos, no puede votar más de una vez.");
                     }
                 }
             }
         }

         return baseRepository.save(t);
     }
}
