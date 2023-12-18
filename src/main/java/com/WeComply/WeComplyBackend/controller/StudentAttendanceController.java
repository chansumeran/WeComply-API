package com.WeComply.WeComplyBackend.controller;

import com.WeComply.WeComplyBackend.dto.AttendanceResponse;
import com.WeComply.WeComplyBackend.dto.FormattedStudentResponse;
import com.WeComply.WeComplyBackend.dto.StudentAttendanceSummaryResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student-attendance-summary")
public class StudentAttendanceController {

    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private AttendanceServiceImpl attendanceService;
    @Autowired
    private SanctionRepository sanctionRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping("/student-and-attendance-summary/{studentId}")
    public ResponseEntity<StudentAttendanceSummaryResponse> getStudentAndAttendanceSummary(
            @PathVariable Integer studentId) {

        // Retrieve student information with sanction
        Optional<Student> studentWithSanction = studentService.getStudentWithSanction(studentId);

        if (studentWithSanction.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Student student = studentWithSanction.get();

        Integer studentID = student.getStudentId();
        String fullName = student.getFirstName() + " " + student.getLastName();
        String info = student.getDepartmentCode() + ", " + student.getCourse() + "-" + student.getYearLevel();
        Integer totalAbsences = calculateOverallAbsences(studentID);
        Sanction sanction = assignSanction(totalAbsences);

        FormattedStudentResponse studentResponse = new FormattedStudentResponse();
        studentResponse.setFullName(fullName);
        studentResponse.setInfo(info);
        studentResponse.setSanction(sanction);

        // Retrieve attendance summary for the student
        List<AttendanceResponse> attendanceSummaryList = attendanceService.getAttendanceSummary(studentId);

        // Create a combined response object
        StudentAttendanceSummaryResponse combinedResponse = new StudentAttendanceSummaryResponse();
        combinedResponse.setStudentResponse(studentResponse);
        combinedResponse.setAttendanceSummaryList(attendanceSummaryList);

        return new ResponseEntity<>(combinedResponse, HttpStatus.OK);
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
