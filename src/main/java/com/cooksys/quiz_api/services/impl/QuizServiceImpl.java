package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {

  private QuizRepository quizRepository;
  private QuizMapper quizMapper;
  private QuestionRepository questionRepository;
  private AnswerRepository answerRepository;
  private QuestionMapper questionMapper;

  @Override
  public List<QuizResponseDto> getAllQuizzes() {

    return quizMapper.entitiesToDtos(quizRepository.findAll());
  }

  @Override
  public ResponseEntity<QuizResponseDto> createQuiz(QuizRequestDto quizRequestDto) {
    Quiz incomingQuiz = quizMapper.requestDtoToEntity(quizRequestDto);
    quizRepository.saveAndFlush(incomingQuiz);
    for (Question question : incomingQuiz.getQuestions()) {
      questionRepository.saveAndFlush(question);
      question.setQuiz(incomingQuiz);
      for (Answer answer : question.getAnswers()) {
        answerRepository.saveAndFlush(answer);
        answer.setQuestion(question);
      }
    }
    Quiz savedQuiz = quizRepository.saveAndFlush(incomingQuiz);
    return new ResponseEntity<QuizResponseDto>
            (quizMapper.entityToDto(Optional.of((incomingQuiz))), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<QuizResponseDto> deleteQuiz(Long id) {
    Optional<Quiz> toDelete = quizRepository.findById(id);
    if (toDelete.isPresent()) {
      Quiz quiz = toDelete.get();
      quizRepository.delete(quiz);
      return new ResponseEntity<>(quizMapper.entityToDto(Optional.of(quiz)), HttpStatus.OK);
    } else {
      throw new IllegalArgumentException("no quiz exists");
    }

  }


  @Override
  public ResponseEntity<QuizResponseDto> updateQuiz(Long id, String newName) {
    Optional<Quiz> toUpdate = quizRepository.findById(id);
    if (toUpdate.isPresent()) {
      Quiz updatedQuiz = toUpdate.get();
      updatedQuiz.setName("newQuiz");
      quizRepository.saveAndFlush(updatedQuiz);
      return new ResponseEntity<>(quizMapper.entityToDto(Optional.of(updatedQuiz)), HttpStatus.OK);
    } else {
      throw new IllegalArgumentException("Cannot update this quiz name");
    }

  }

  @Override
  public ResponseEntity<QuestionResponseDto> getRandomQuestion(Long id) {
    Optional<Quiz> quiz = quizRepository.findById(id);

    if (quiz.isPresent()) {
      Quiz chosenQuiz = quiz.get();
      List<Question> questions = chosenQuiz.getQuestions();

      Random randomQuestionShuffle = new Random();
      int randomIndexInList = randomQuestionShuffle.nextInt(questions.size());
      Question randomQuestionChosen = questions.get(randomIndexInList);
      return new ResponseEntity<QuestionResponseDto>(questionMapper.entityToDto(Optional.ofNullable(randomQuestionChosen)), HttpStatus.OK);
    } else {
      throw new IllegalArgumentException("Question does not exist");
    }
  }

  @Override
  public ResponseEntity<QuizResponseDto> addToQuiz(Long id, QuestionRequestDto questionRequestDto) {
    Optional<Quiz> quiz = quizRepository.findById(id);

    if (quiz.isPresent()) {
      Quiz thisQuiz = quiz.get();
      Question question = questionMapper.requestDtoToEntity(questionRequestDto);
      for (Answer answer : question.getAnswers()) {
        answer.setQuestion(question);
      }
      question.setQuiz(thisQuiz);
      questionRepository.saveAndFlush(question);
      return new ResponseEntity<QuizResponseDto>(quizMapper.entityToDto(Optional.of(thisQuiz)), HttpStatus.OK);
    } else {
      throw new IllegalArgumentException("Cannot add question to quiz");
    }
  }

  @Override
  public ResponseEntity<QuestionResponseDto> deleteQuestion(Long id, Long questionId) {
    Optional<Quiz> quiz = quizRepository.findById(id);
    if (quiz.isPresent()) {
      Quiz chosenQuiz = quiz.get();
      List<Question> questions = chosenQuiz.getQuestions();
      questionRepository.findById(questionId);

    }
    Optional<Question> question = questionRepository.findById(questionId);
    if (question.isPresent()) {
      Question chosenQuestion = question.get();
      questionRepository.delete(chosenQuestion);
      return new ResponseEntity<QuestionResponseDto>(questionMapper.entityToDto(Optional.of(chosenQuestion)), HttpStatus.OK);
    } else {
      throw new IllegalArgumentException("Cannot delete this question");
    }


  }
}
