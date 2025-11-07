package com.emoji.mymoji.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.ConnectionBuilder;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    private int score;

    @CreationTimestamp // 엔티티 생성 시 현재 시간 자동 저장
    @Column(name = "created_at", updatable = false) // 생성시에만 저장, 업데이트 불가
    private LocalDateTime createdAt;

}
