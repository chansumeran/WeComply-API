package com.WeComply.WeComplyBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllStudentResponse {

    private Integer studentId;

    private String fullName;

    private String department;

    private String course;

    private Integer yearLevel;

    private Integer totalAbsences;

    private Integer sanctionId;

    private String sanctionDescription;

}
