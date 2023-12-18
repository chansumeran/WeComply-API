package com.WeComply.WeComplyBackend.service;

import com.WeComply.WeComplyBackend.dto.AttendanceResponse;
import com.WeComply.WeComplyBackend.dto.FormattedStudentResponse;
import com.WeComply.WeComplyBackend.dto.StudentAttendanceSummaryResponse;
import com.WeComply.WeComplyBackend.dto.StudentResponse;
import com.WeComply.WeComplyBackend.entity.Sanction;
import com.WeComply.WeComplyBackend.entity.Student;
import com.WeComply.WeComplyBackend.repository.AttendanceRepository;
import com.WeComply.WeComplyBackend.repository.SanctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentAttendanceServiceImpl {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private SanctionRepository sanctionRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    public Optional<StudentAttendanceSummaryResponse> getStudentAndAttendanceSummary(Integer studentId) {
        Optional<Student> studentWithSanction = studentService.getStudentWithSanction(studentId);

        if (studentWithSanction.isEmpty()) {
            return Optional.empty();
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

        List<AttendanceResponse> attendanceSummaryList = attendanceService.getAttendanceSummary(studentId);

        StudentAttendanceSummaryResponse combinedResponse = new StudentAttendanceSummaryResponse();
        combinedResponse.setStudentResponse(studentResponse);
        combinedResponse.setAttendanceSummaryList(attendanceSummaryList);

        return Optional.of(combinedResponse);
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
