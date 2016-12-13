package me.ramseyboy.qnews.api.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Answer answer;

    private String question;

    private Date timestamp;

    Question() {
        // no args for hibernate
    }

    public Question(String question, Date timestamp) {
        this.question = question;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
