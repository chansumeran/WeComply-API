package com.WeComply.WeComplyBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAttendanceSummaryResponse {

    private Optional<StudentResponse> studentResponse;
    private List<AttendanceResponse> attendanceSummaryList;

}
