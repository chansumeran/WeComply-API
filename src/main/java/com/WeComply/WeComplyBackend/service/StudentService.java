package com.WeComply.WeComplyBackend.service;

import com.WeComply.WeComplyBackend.dto.GetAllStudentResponse;
import com.WeComply.WeComplyBackend.dto.StudentResponse;
import com.WeComply.WeComplyBackend.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Optional<StudentResponse> getStudentSummary(Integer studentId);

    Optional<Student> getStudentWithSanction(Integer studentId);

    List<GetAllStudentResponse> getFilteredStudents(String deptCode, String course, Integer yearLevel, String eventCode);

    List<GetAllStudentResponse> getAllStudents();

}
