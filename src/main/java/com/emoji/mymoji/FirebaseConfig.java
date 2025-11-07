package com.emoji.mymoji;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseApp() throws Exception{
        //resources 폴더의 서비스 계정 키 파일을 읽어옵니다.
        //InputStream serviceAccount= new ClassPathResource("serviceAccountKey.json").getInputStream();
        InputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

        //firebase 프로젝트에 인증하고 연결하는 데 필요한 설정(옵션) 객체를 만드는 과정
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        //FirebaseApp이 이미 초기화되었는지 확인
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }

    @Bean
    public FirebaseAuth firebaseAuth() throws Exception{
        //FirebaseApp 빈을 주입받아 FirebaseAuth 인스턴스를 생성
        return FirebaseAuth.getInstance(firebaseApp());
    }
}
