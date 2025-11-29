package com.modhtom.questionservice.controller;


import com.modhtom.questionservice.model.Question;
import com.modhtom.questionservice.model.QuestionWrapper;
import com.modhtom.questionservice.model.Response;
import com.modhtom.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return  questionService.addQuestion(question);
    }


    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestions(@RequestParam String categoryName,@RequestParam Integer questionsNumber){
        return questionService.generateQuestions(categoryName,questionsNumber);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

}