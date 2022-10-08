package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import org.springframework.http.ResponseEntity;

public interface QuizService {

  List<QuizResponseDto> getAllQuizzes();


  ResponseEntity<QuizResponseDto> createQuiz(QuizRequestDto quizRequestDto);

  ResponseEntity<QuizResponseDto> deleteQuiz(Long id);

    ResponseEntity<QuizResponseDto> updateQuiz(Long id, String newQuiz);

  ResponseEntity<QuestionResponseDto> getRandomQuestion(Long id);

  ResponseEntity<QuizResponseDto> addToQuiz(Long id, QuestionRequestDto questionRequestDto);

  ResponseEntity<QuestionResponseDto> deleteQuestion(Long id, Long questionId);

}
