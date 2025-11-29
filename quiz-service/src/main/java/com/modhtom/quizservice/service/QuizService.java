package com.modhtom.quizservice.service;

import com.modhtom.quizservice.dao.QuizDao;
import com.modhtom.quizservice.feign.QuizInterface;
import com.modhtom.quizservice.model.QuestionWrapper;
import com.modhtom.quizservice.model.Quiz;
import com.modhtom.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questionIds = quizInterface.generateQuestions(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        return quizInterface.getQuestions(questionIds);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        return quizInterface.getScore(responses);
    }
}