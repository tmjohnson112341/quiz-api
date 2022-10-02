package com.cooksys.quiz_api.mappers;

import java.util.List;

import com.cooksys.quiz_api.dtos.AnswerRequestDto;
import com.cooksys.quiz_api.dtos.AnswerResponseDto;
import com.cooksys.quiz_api.entities.Answer;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

	AnswerResponseDto entityToDto(Answer entity);

	Answer dtoToEntity(AnswerRequestDto answerRequestDto);

	List<AnswerResponseDto> entitiesToDtos(List<Answer> entities);
}
