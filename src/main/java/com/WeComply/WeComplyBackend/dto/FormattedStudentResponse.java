package com.WeComply.WeComplyBackend.dto;

import com.WeComply.WeComplyBackend.entity.Sanction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormattedStudentResponse {

    private String fullName;

    private String info;

    private Sanction sanction;

}
