package com.cooksys.quiz_api.repositories;

import com.cooksys.quiz_api.entities.Answer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	Optional<Answer> findById(Long id);
}
