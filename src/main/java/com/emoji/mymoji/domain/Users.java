package com.emoji.mymoji.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import jakarta.persistence.Id;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue
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



}
