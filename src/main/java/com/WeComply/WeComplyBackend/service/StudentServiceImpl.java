package com.WeComply.WeComplyBackend.service;

import com.WeComply.WeComplyBackend.dto.GetAllStudentResponse;
import com.WeComply.WeComplyBackend.entity.Sanction;
import com.WeComply.WeComplyBackend.entity.Student;
import com.WeComply.WeComplyBackend.repository.AttendanceRepository;
import com.WeComply.WeComplyBackend.repository.SanctionRepository;
import com.WeComply.WeComplyBackend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SanctionRepository sanctionRepository;

    @Override
    public Optional<Student> getStudentWithSanction(Integer studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public List<Student> getFilteredStudents(String deptCode, String course, Integer yearLevel) {
        return studentRepository.findByDynamicFilters(deptCode, course, yearLevel);
    }

    @Override
    public List<GetAllStudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        List<GetAllStudentResponse> studentResponses = new ArrayList<>();

        for (Student student : students) {
            // get info to be passed in the dto
            Integer studentId = student.getStudentId();
            String fullName = student.getFirstName() + " " + student.getLastName();
            String department = student.getDepartment().getDepartmentCode();
            String course = student.getCourse();
            Integer yearLevel = student.getYearLevel();
            Integer totalAbsences = calculateOverallAbsences(studentId);
            Integer sanctionId = student.getSanction().getSanctionId();
            String sanction = assignSanction(totalAbsences);

            GetAllStudentResponse studentResponse = new GetAllStudentResponse();
            studentResponse.setStudentId(studentId);
            studentResponse.setFullName(fullName);
            studentResponse.setDepartment(department);
            studentResponse.setCourse(course);
            studentResponse.setYearLevel(yearLevel);
            studentResponse.setTotalAbsences(totalAbsences);
            studentResponse.setSanctionId(sanctionId);
            studentResponse.setSanctionDescription(sanction);
            studentResponses.add(studentResponse);
        }
        return studentResponses;
    }

    private String assignSanction(Integer absences) {
        // This will return all sanctions in the database
        List<Sanction> sanctions = sanctionRepository.findAll();

        String sanctionDesc = "No Sanction"; // Default value if absences are 0 or negative

        if (absences > 0) {
            for (Sanction sanction : sanctions) {
                // Compare absences with trigger values
                if (absences >= sanction.getTriggerValue()) {
                    sanctionDesc = sanction.getDescription();
                }
            }
        }

        return sanctionDesc;
    }


    private Integer calculateOverallAbsences(Integer studentId) {
        Integer absences = attendanceRepository.calculateOverall(studentId);

        if (absences == null) {
            return 0;
        }

        return absences;
    }



}
