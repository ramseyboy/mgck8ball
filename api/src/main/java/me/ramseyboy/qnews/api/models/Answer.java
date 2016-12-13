package me.ramseyboy.qnews.api.models;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Question question;

    private String answer;

    Answer() {
        // no args for hibernate
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
