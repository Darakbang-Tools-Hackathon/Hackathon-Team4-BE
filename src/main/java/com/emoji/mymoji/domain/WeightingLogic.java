package com.emoji.mymoji.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class WeightingLogic {
    @Id
    @GeneratedValue
    @Column(name = "logic_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    private String attributeName;
    private int logicScore;
    private double value; //가중치
}
