package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
@Service
public class QuizService {

    @Autowired
    UserService userService;
    @Autowired
    QuizRepository quizRepository;

    public Quiz saveQuiz(Quiz quiz) {
        quiz.setAnswer(quiz.getAnswer() == null ? new ArrayList<>() : quiz.getAnswer());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByEmail(((UserDetails) principal).getUsername());
        quiz.setCreator(user);
        return quizRepository.save(quiz);
    }

    public boolean existsById(Integer id) {
        return quizRepository.existsById(id);
    }

    public Quiz getQuizById(Integer id) {
        return quizRepository.findById(id).get();
    }

    public Page<Quiz> getAllQuizzes(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return quizRepository.findAll(pageable);
    }

    public void deleteQuizById(Integer id) {
        quizRepository.deleteById(id);
    }

}
