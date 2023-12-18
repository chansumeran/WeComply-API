package com.WeComply.WeComplyBackend.service;

import com.WeComply.WeComplyBackend.dto.GetAllStudentResponse;
import com.WeComply.WeComplyBackend.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
//    List<Student> getFilteredStudents(String eventName, String deptCode, String course, Integer yearLevel);

    Optional<Student> getStudentWithSanction(Integer studentId);

    List<Student> getFilteredStudents(String deptCode, String course, Integer yearLevel);

    List<GetAllStudentResponse> getAllStudents();
//
//    List<Student> getStudentsByEvent(String eventName);

}
