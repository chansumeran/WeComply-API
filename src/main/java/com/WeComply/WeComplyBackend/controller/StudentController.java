package com.WeComply.WeComplyBackend.controller;

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
    @GetMapping("/advance_filter")
    public ResponseEntity<List<Student>> getAdvanceFilteredStudents(
            @RequestParam(name = "deptCode", required = false) String deptCode,
            @RequestParam(name = "course", required = false) String course,
            @RequestParam(name = "yearLevel", required = false) String yearLevel) {
        List<Student> students = studentService.getFilteredStudents(deptCode, course, yearLevel);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/event_filter")
    public ResponseEntity<List<Student>> getEventFilteredStudents(
            @RequestParam(name = "eventName", required = false) String eventName) {
        List<Student> studentsByEvent = studentService.getStudentsByEvent(eventName);
        return new ResponseEntity<>(studentsByEvent, HttpStatus.OK);
    }

    @GetMapping("/{studentID}")
    public ResponseEntity<StudentResponse> getStudentWithSanction(@PathVariable("studentID") int studentID) {
        Optional<Student> studentWithSanction = studentService.getStudentWithSanction(studentID);

        if (studentWithSanction.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Student student = studentWithSanction.get();

        String fullName = student.getFirstName() + " " + student.getLastName();
        String info = student.getDepartmentCode() + ", " + student.getCourse() + "-" + student.getYearLevel();
        String sanction = student.getSanction().getDescription();

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setFullName(fullName);
        studentResponse.setInfo(info);
        studentResponse.setSanction(sanction);

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

}
