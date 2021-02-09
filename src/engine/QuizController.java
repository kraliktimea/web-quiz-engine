package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    UserService userService;
    @Autowired
    CompletionsService completionsService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public QuizController() {
    }

    //create quiz
    @PostMapping(path = "/api/quizzes")
    public ResponseEntity<Quiz> createQuiz(@Valid @RequestBody Quiz newQuiz) {
        Quiz createdQuiz = quizService.saveQuiz(newQuiz);
        return new ResponseEntity<>(createdQuiz, HttpStatus.OK);
    }

    //get one quiz by id
    @GetMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int id) {
        if (quizService.existsById(id)) {
            Quiz quiz = quizService.getQuizById(id);
            return new ResponseEntity<Quiz>(quiz, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "quiz not found");
        }
    }

    //get all quizzes, pageable
    @GetMapping(path = "/api/quizzes")
    public ResponseEntity<Page<Quiz>> getAll(@RequestParam(defaultValue = "0") Integer page) {
        Page<Quiz> onePageOfQuizzes = quizService.getAllQuizzes(page);
        return new ResponseEntity<>(onePageOfQuizzes, HttpStatus.OK);
    }

    //solve a quiz by id
    @PostMapping(path = "/api/quizzes/{id}/solve")
    public Answer solveOneQuiz(@PathVariable int id, @RequestBody Solution solution) {
        Answer answerToReturn;
        if (!quizService.existsById(id)) {
            throw new QuestNotFoundException("Not found quest");
        }
        Quiz solvedQuiz = quizService.getQuizById(id);

        if ((solution.getAnswer().equals(solvedQuiz.getAnswer()))) {
            answerToReturn = Answer.CORRECT_ANSWER;
            completionsService.saveCompletion(id);
        } else {
            answerToReturn = Answer.WRONG_ANSWER;
        }
        return answerToReturn;
    }

    //delete a quiz by id
    @DeleteMapping(path = "/api/quizzes/{id}")
    public void deleteQuiz(@PathVariable int id) {
        if (quizService.existsById(id)) {
            if (quizService.getQuizById(id).getCreator().getUserId().equals(userService.findUserByEmail(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).getUserId())) {
                quizService.deleteQuizById(id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "deleted successfully");
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not authorized");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no such quiz found");
        }
    }

    //get completions by userId
    @GetMapping(path = "/api/quizzes/completed")
    public Page<Completions> getCompleted(@RequestParam(defaultValue = "0") Integer page) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByEmail(((UserDetails) principal).getUsername());
        Integer userId = user.getUserId();

        return completionsService.findCompletionsByUserId(userId, page);
    }

    //endpoint for test
    @PostMapping(path = "/actuator/shutdown")
    public void shutdown() {
    }

}
