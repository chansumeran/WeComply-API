package com.WeComply.WeComplyBackend.service;

import com.WeComply.WeComplyBackend.dto.AttendanceResponse;
import com.WeComply.WeComplyBackend.dto.StudentResponse;
import com.WeComply.WeComplyBackend.dto.StudentAttendanceSummaryResponse;
import com.WeComply.WeComplyBackend.repository.AttendanceRepository;
import com.WeComply.WeComplyBackend.repository.SanctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentAttendanceServiceImpl implements StudentAttendanceService {
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private AttendanceService attendanceService;

    @Override
    public Optional<StudentAttendanceSummaryResponse> getStudentAndAttendanceSummary(Integer studentId) {
        Optional<StudentResponse> studentSummary = studentService.getStudentSummary(studentId);
        List<AttendanceResponse> attendanceSummaryList = attendanceService.getAttendanceSummary(studentId);

        StudentAttendanceSummaryResponse combinedResponse = new StudentAttendanceSummaryResponse();
        combinedResponse.setStudentResponse(studentSummary);
        combinedResponse.setAttendanceSummaryList(attendanceSummaryList);

        return Optional.of(combinedResponse);
    }
}
