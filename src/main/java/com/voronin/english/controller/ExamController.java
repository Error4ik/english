package com.voronin.english.controller;

import com.voronin.english.domain.Exam;
import com.voronin.english.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 08.12.2018.
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @RequestMapping("/{id}")
    public Exam getExam(final @PathVariable UUID id) {
        return this.examService.getExamById(id);
    }

    @RequestMapping("/exams")
    public List<Exam> getExams() {
        return this.examService.getExams();
    }
}
