package me.ramseyboy.qnews.api.core;

import me.ramseyboy.qnews.api.models.Answer;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
class MockCoreService implements CoreService {

    private static String[] answers = new String[]{"No", "Yes", "Maybe", "Possibly", "Ask again"};

    public Answer askQuestion(String question) {
        Random random = new Random(answers.length);
        return new Answer(answers[random.nextInt(answers.length)]);
    }
}
