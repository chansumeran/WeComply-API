package com.WeComply.WeComplyBackend.controller;

import com.WeComply.WeComplyBackend.dto.GetAllStudentResponse;
import com.WeComply.WeComplyBackend.dto.StudentResponse;
import com.WeComply.WeComplyBackend.entity.Student;
import com.WeComply.WeComplyBackend.service.AttendanceServiceImpl;
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

    @Autowired
    private AttendanceServiceImpl attendanceService;

    // ADVANCED FILTER
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> getAdvanceFilteredStudents(
            @RequestParam(name = "departmentCode", required = false) String deptCode,
            @RequestParam(name = "course", required = false) String course,
            @RequestParam(name = "yearLevel", required = false) Integer yearLevel) {

        List<Student> students = studentService.getFilteredStudents(deptCode, course, yearLevel);

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetAllStudentResponse>> getAllStudents() {
        List<GetAllStudentResponse> allStudents = studentService.getAllStudents();

        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }

    @GetMapping("/{studentID}")
    public ResponseEntity<?> getStudentWithSanction(@PathVariable("studentID") int studentID) {
        Optional<Student> studentWithSanction = studentService.getStudentWithSanction(studentID);

        if (studentWithSanction.isEmpty()) {
            return new ResponseEntity<>("No student found.", HttpStatus.NOT_FOUND);
        }

        Student student = studentWithSanction.get();

        String fullName = student.getFirstName() + " " + student.getLastName();
        String info = student.getDepartmentCode() + ", " + student.getCourse() + "-" + student.getYearLevel();
        String sanctionDescription = student.getSanction().getDescription();

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setFullName(fullName);
        studentResponse.setInfo(info);
        studentResponse.setSanctionDescription(sanctionDescription);

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

}
