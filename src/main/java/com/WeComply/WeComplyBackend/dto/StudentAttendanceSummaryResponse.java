package com.WeComply.WeComplyBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAttendanceSummaryResponse {

    private FormattedStudentResponse studentResponse;
    private List<AttendanceResponse> attendanceSummaryList;

}
