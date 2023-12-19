package com.WeComply.WeComplyBackend.controller;

import com.WeComply.WeComplyBackend.dto.StudentAttendanceSummaryResponse;
import com.WeComply.WeComplyBackend.service.StudentAttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/student-attendance-summary")
public class StudentAttendanceController {

    @Autowired
    private StudentAttendanceServiceImpl studentAttendanceService;

    @GetMapping("/{studentId}")
    public ResponseEntity<Optional<StudentAttendanceSummaryResponse>> getStudentAndAttendanceSummary(
            @PathVariable Integer studentId) {

        Optional<StudentAttendanceSummaryResponse> studentAndAttendanceSummary =
                studentAttendanceService.getStudentAndAttendanceSummary(studentId);

        return new ResponseEntity<>(studentAndAttendanceSummary, HttpStatus.OK);
    }
}
