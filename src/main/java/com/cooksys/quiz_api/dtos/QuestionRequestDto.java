package com.cooksys.quiz_api.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionRequestDto {
    private String text;
    private List<AnswerRequestDto> answers;
}
