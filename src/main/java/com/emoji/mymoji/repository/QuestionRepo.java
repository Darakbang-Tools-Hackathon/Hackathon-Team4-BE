package com.emoji.mymoji.repository;

import com.emoji.mymoji.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM question ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Question> findRandomQuestions(@Param("count") int count);
}
