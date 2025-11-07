package com.emoji.mymoji.repository;

import com.emoji.mymoji.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    // Firebase UID로 사용자를 찾는 메소드 (로그인 시 필수)
    Optional<Users> findByUid(String uid);

    // 이메일로 사용자를 찾는 메소드 (중복 가입 방지 시 유용)
    Optional<Users> findByEmail(String email);
}
