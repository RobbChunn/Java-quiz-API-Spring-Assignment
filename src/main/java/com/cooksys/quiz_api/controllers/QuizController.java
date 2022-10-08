package com.cooksys.quiz_api.controllers;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  @GetMapping
  public List<QuizResponseDto> getAllQuizzes() {

    return quizService.getAllQuizzes();
  }
  
  // TODO: Implement the remaining 6 endpoints from the documentation.

  @PostMapping
  public ResponseEntity<QuizResponseDto> createQuiz(@RequestBody QuizRequestDto quizRequestDto) {
    return quizService.createQuiz(quizRequestDto);
  }

  @DeleteMapping ("/{id}")
  public ResponseEntity<QuizResponseDto> deleteQuiz(@PathVariable Long id){

    return quizService.deleteQuiz(id);
  }

  @PatchMapping ("/{id}/rename/{newQuiz}")
  public ResponseEntity<QuizResponseDto> updateQuiz(@PathVariable Long id, @PathVariable String newQuiz){
    return quizService.updateQuiz(id, newQuiz);
  }

  @GetMapping ("/{id}/random")
  public ResponseEntity<QuestionResponseDto> getRandomQuestion(@PathVariable Long id){
    return quizService.getRandomQuestion(id);
  }

  @PatchMapping ("/{id}/add")
  public ResponseEntity<QuizResponseDto> addToQuiz(@PathVariable Long id, @RequestBody QuestionRequestDto questionRequestDto){
    return quizService.addToQuiz(id, questionRequestDto);
  }

  @DeleteMapping ("/{id}/delete/{questionId}")
  public ResponseEntity<QuestionResponseDto> deleteQuestion(@PathVariable Long id, @PathVariable Long questionId){
    return quizService.deleteQuestion(id, questionId);
  }
}
