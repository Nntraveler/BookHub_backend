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


}
