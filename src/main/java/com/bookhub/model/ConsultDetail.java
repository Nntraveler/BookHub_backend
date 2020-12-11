package com.bookhub.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ConsultDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String question;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="question_time")
    private Date questionTime;

    private String answer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="answer_time")
    private Date answerTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="consult_id")
    private Consultation consultation;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(Date questionTime) {
        this.questionTime = questionTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public Consultation consultationInstance() {
        return consultation;
    }

    public Integer getConsultation() {
        return consultation.getId();
    }
    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
}
