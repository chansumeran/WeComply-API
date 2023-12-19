package com.WeComply.WeComplyBackend.controller;

import com.WeComply.WeComplyBackend.dto.AttendanceResponse;
import com.WeComply.WeComplyBackend.entity.Attendance;
import com.WeComply.WeComplyBackend.service.AttendanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceServiceImpl attendanceService;

    @GetMapping("/calculate-absences")
    public void calculateTotalAbsences() {
        attendanceService.calculateTotalAbsences();
    }

}
