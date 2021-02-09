package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Component
@Service
public class CompletionsService {

    @Autowired
    CompletionsRepository completionsRepository;
    @Autowired
    UserService userService;

    //get completions by userId
    Page<Completions> findCompletionsByUserId(Integer userId, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return completionsRepository.findAllByUserId(userId, pageable);
    }

    //save completion
    public void saveCompletion(Integer quizId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByEmail(((UserDetails) principal).getUsername());
        Integer userId = user.getUserId();

        Completions oneCompletion = new Completions();
        oneCompletion.setUserId(userId);
        oneCompletion.setCompletedAt(LocalDateTime.now());
        oneCompletion.setId(quizId);

        completionsRepository.save(oneCompletion);
    }
}
