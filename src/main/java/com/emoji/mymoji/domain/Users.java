package com.emoji.mymoji.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    //firebase uid
    private String uid;

    @Column(length=20)
    private String nickname;
    private String email;

    @Builder.Default
    private double attribute1=50.0; //정서안정성
    @Builder.Default
    private double attribute2=50.0; //외향성
    @Builder.Default
    private double attribute3=50.0; //친화성
    @Builder.Default
    private double attribute4=50.0; //성실성
    @Builder.Default
    private double attribute5=50.0; //개방성

    /**
     * (추가) 계산된 점수 변동값을 현재 특성치에 적용하는 메소드
     * @param scoreChanges {"정서안정성": +0.5, "외향성": -0.1, ...} 형태의 맵
     */
    public void applyScoreChanges(Map<String, Double> scoreChanges) {
        // 1. 각 특성치에 변동값 더하기
        this.attribute1 += scoreChanges.getOrDefault("정서안정성", 0.0);
        this.attribute2 += scoreChanges.getOrDefault("외향성", 0.0);
        this.attribute3 += scoreChanges.getOrDefault("친화성", 0.0);
        this.attribute4 += scoreChanges.getOrDefault("성실성", 0.0);
        this.attribute5 += scoreChanges.getOrDefault("개방성", 0.0);

        // 2. (중요) 점수가 0~100 사이를 벗어나지 않도록 보정 (Clamping)
        this.attribute1 = Math.max(0, Math.min(100, this.attribute1));
        this.attribute2 = Math.max(0, Math.min(100, this.attribute2));
        this.attribute3 = Math.max(0, Math.min(100, this.attribute3));
        this.attribute4 = Math.max(0, Math.min(100, this.attribute4));
        this.attribute5 = Math.max(0, Math.min(100, this.attribute5));
    }


}
