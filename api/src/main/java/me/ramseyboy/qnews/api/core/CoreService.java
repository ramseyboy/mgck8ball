package me.ramseyboy.qnews.api.core;

import me.ramseyboy.qnews.api.models.Answer;
import org.springframework.stereotype.Service;

@Service
public interface CoreService {
    Answer askQuestion(String question);
}
