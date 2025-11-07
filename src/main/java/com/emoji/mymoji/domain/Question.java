package com.emoji.mymoji.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long questionId;

    private String content;

}
