package com.emoji.mymoji.service;

import com.emoji.mymoji.domain.Users;
import com.emoji.mymoji.dto.userDto.AttributeResponse;
import com.emoji.mymoji.dto.userDto.SignUpRequest;
import com.emoji.mymoji.dto.userDto.UserResponse;
import com.emoji.mymoji.repository.UserRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 주입(여기선 firebaseAuth와 userRepo)
public class UserService {

    private final FirebaseAuth firebaseAuth;
    private final UserRepo userRepo;

    @Transactional
    public UserResponse signUp(SignUpRequest request) throws Exception{
        // 1. 우리 DB에 이미 가입된 이메일인지 확인
        if (userRepo.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. Firebase Auth에 사용자 생성
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                .setEmail(request.email())
                .setPassword(request.password())
                .setDisplayName(request.nickname());

        UserRecord userRecord = firebaseAuth.createUser(createRequest);
        String uId = userRecord.getUid();

        // 3. Firebase에서 받은 UID와 정보로 우리 DB(PostgreSQL)에 사용자 저장
        Users newUser = Users.builder()
                .uid(uId)
                .email(request.email())
                .nickname(request.nickname())
                // attribute 1~5는 기본값 50.0으로 자동 설정됨 (Entity의 @Builder.Default)
                .build();

        userRepo.save(newUser);

        return new UserResponse(uId, newUser.getEmail(), newUser.getNickname());
    }

    //특성치
    @Transactional(readOnly = true)
    public AttributeResponse getAttributes(String uid) {
        // UID로 우리 DB에서 사용자를 찾음
        Users user = userRepo.findByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. uid: " + uid));

        // DTO로 변환하여 반환
        return new AttributeResponse(
                user.getAttribute1(),
                user.getAttribute2(),
                user.getAttribute3(),
                user.getAttribute4(),
                user.getAttribute5()
        );
    }
}
