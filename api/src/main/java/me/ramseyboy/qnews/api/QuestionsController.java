package me.ramseyboy.qnews.api;

import me.ramseyboy.qnews.api.core.CoreService;
import me.ramseyboy.qnews.api.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@RestController
public class QuestionsController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CoreService coreService;

    @Autowired
    public QuestionsController(QuestionRepository questionRepository,
                               AnswerRepository answerRepository,
                               CoreService coreService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.coreService = coreService;
    }

    @RequestMapping("/")
    public String index() {
        return "Index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/question")
    public ResponseEntity<?> askQuestion(@RequestBody AskQuestion inputQuestion) {
        if (inputQuestion == null || inputQuestion.getQuestion() == null || inputQuestion.getQuestion().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Answer answer = answerRepository.save(coreService.askQuestion(inputQuestion.getQuestion()));

        Question question = questionRepository.save(new Question(inputQuestion.getQuestion(), new Date()));

        question.setAnswer(answer);
        answer.setQuestion(question);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(question.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/question")
    public Collection<Question> allQuestions() {
        return questionRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/question/{id}")
    public ResponseEntity<?> questionById(@PathVariable Long id) {
        Optional<Question> question = Optional.ofNullable(questionRepository.findOne(id));
        if (question.isPresent()) {
            return ResponseEntity.ok(question.get());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/answer")
    public Collection<Answer> answers() {
        return answerRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/answer/{id}")
    public ResponseEntity<?> answerById(@PathVariable Long id) {
        Optional<Answer> answer = Optional.ofNullable(answerRepository.findOne(id));
        if (answer.isPresent()) {
            return ResponseEntity.ok(answer.get());
        }
        return ResponseEntity.notFound().build();
    }
}