package com.WeComply.WeComplyBackend.controller;

import com.WeComply.WeComplyBackend.dto.GetAllStudentResponse;
import com.WeComply.WeComplyBackend.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    // ADVANCED FILTER
    @GetMapping("/filter")
    public ResponseEntity<List<GetAllStudentResponse>> getAdvanceFilteredStudents(
            @RequestParam(name = "departmentCode", required = false) String deptCode,
            @RequestParam(name = "course", required = false) String course,
            @RequestParam(name = "yearLevel", required = false) Integer yearLevel,
            @RequestParam(name = "eventCode", required = false) String eventCode) {

        List<GetAllStudentResponse> students = studentService.getFilteredStudents(deptCode, course, yearLevel, eventCode);

        return new ResponseEntity<>(students, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<GetAllStudentResponse>> getAllStudents() {
        List<GetAllStudentResponse> allStudents = studentService.getAllStudents();

        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }

}
