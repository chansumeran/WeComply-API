package com.WeComply.WeComplyBackend.controller;

import com.WeComply.WeComplyBackend.dto.GetAllStudentResponse;
import com.WeComply.WeComplyBackend.dto.StudentResponse;
import com.WeComply.WeComplyBackend.entity.Sanction;
import com.WeComply.WeComplyBackend.entity.Student;
import com.WeComply.WeComplyBackend.repository.AttendanceRepository;
import com.WeComply.WeComplyBackend.repository.SanctionRepository;
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

    @Autowired
    private SanctionRepository sanctionRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

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

        Integer studentId = student.getStudentId();
        String fullName = student.getFirstName() + " " + student.getLastName();
        String info = student.getDepartmentCode() + ", " + student.getCourse() + "-" + student.getYearLevel();
        Integer totalAbsences = calculateOverallAbsences(studentId);
        Sanction sanction = assignSanction(totalAbsences);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setFullName(fullName);
        studentResponse.setInfo(info);
        studentResponse.setSanction(sanction);

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    private Sanction assignSanction(Integer absences) {
        // This will return all sanctions in the database
        List<Sanction> sanctions = sanctionRepository.findAll();

        Sanction matchedSanction = null;

        if (absences > 0) {
            for (Sanction sanction : sanctions) {
                // Compare absences with trigger values
                if (absences >= sanction.getTriggerValue()) {
                    matchedSanction = sanction;
                }
            }
        }

        return matchedSanction;
    }

    private Integer calculateOverallAbsences(Integer studentId) {
        Integer absences = attendanceRepository.calculateOverall(studentId);

        if (absences == null) {
            return 0;
        }

        return absences;
    }

}
