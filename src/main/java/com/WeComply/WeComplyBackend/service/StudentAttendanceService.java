package com.WeComply.WeComplyBackend.service;

import com.WeComply.WeComplyBackend.dto.StudentAttendanceSummaryResponse;
import java.util.Optional;

public interface StudentAttendanceService {
    Optional<StudentAttendanceSummaryResponse> getStudentAndAttendanceSummary(Integer studentId);

}
