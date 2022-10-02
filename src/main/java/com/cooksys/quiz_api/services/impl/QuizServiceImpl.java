package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.exceptions.NotFoundException;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;
	private final QuizMapper quizMapper;
	private final QuestionRepository questionRepository;
	private final QuestionMapper questionMapper;
	private final AnswerRepository answerRepository;

	private Quiz getQuiz(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new NotFoundException("No quiz found with id " + id);
		}
		return optionalQuiz.get();
	}

	private Question getQuestion(Long id) {
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (optionalQuestion.isEmpty()) {
			throw new NotFoundException("No question found with the id: " + id);
		}
		return optionalQuestion.get();
	}

	// tested and working
	@Override
	public List<QuizResponseDto> getAllQuizzes() {
		return quizMapper.entitiesToDtos(quizRepository.findAll());
	}

	// tested and working
	@Override
	public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
		Quiz quizToSave = quizMapper.requestDtoToEntity(quizRequestDto);

		quizRepository.saveAndFlush(quizToSave);

		for (Question question : quizToSave.getQuestions()) {
			question.setQuiz(quizToSave);
			quizToSave.setDeleted(false);
			questionRepository.saveAndFlush(question);

			for (Answer answer : question.getAnswers()) {
				answer.setQuestion(question);
				answerRepository.saveAndFlush(answer);
			}
		}

		return quizMapper.entityToDto(quizToSave);
	}

	// tested and working
	@Override
	public QuizResponseDto deleteQuiz(Long id) {
		Quiz quizToDelete = getQuiz(id);
		quizToDelete.setDeleted(true);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToDelete));
	}

	// tested and working
	@Override
	public QuizResponseDto renameQuiz(Long id, String newName) {
		Quiz quizToRename = getQuiz(id);
		quizToRename.setName(newName);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToRename));
	}

	// tested and working
	@Override
	public QuestionResponseDto randomQuestion(Long id) {
		List<Question> questions = getQuiz(id).getQuestions();
		Random random = new Random();
		Question randomQuestion = questions.get(random.nextInt(questions.size()));
		return questionMapper.entityToDto(randomQuestion);
	}

	// tested and working
	@Override
	public QuizResponseDto addModifiedQuestion(Long id, QuestionRequestDto questionRequestDto) {
		Quiz quizToModify = getQuiz(id);
		Question question = questionMapper.requestDtoToEntity(questionRequestDto);

		question.setQuiz(quizToModify);
		questionRepository.saveAndFlush(question);
		question.setAnswers(question.getAnswers());
		for (Answer answer : question.getAnswers()) {
			answer.setQuestion(question);
			answerRepository.saveAndFlush(answer);
		}
		quizToModify.getQuestions().add(question);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToModify));
	}

	// tested and working?
	@Override
	public QuestionResponseDto deleteQuestion(Long id, Long questionId) {
		Quiz findQuiz = getQuiz(id);
		Question questionToDelete = getQuestion(questionId);
		List<Question> questions = findQuiz.getQuestions();

		for (Question question : questions) {
			if (question.getId() == questionId) {
				question.setDeleted(true);
			}
				
		}
		findQuiz.setQuestions(questions);
		return questionMapper.entityToDto(questionRepository.saveAndFlush(questionToDelete));
	}

}
