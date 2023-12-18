package com.WeComply.WeComplyBackend.dto;

import com.WeComply.WeComplyBackend.entity.Sanction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilteredStudentResponse {

    private Integer studentId;

    private String fullName;

    private String department;

    private String course;

    private Integer yearLevel;

    private Integer totalAbsences;

    private Sanction sanction;

}
