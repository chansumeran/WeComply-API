package com.WeComply.WeComplyBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {
    private LocalDate date;
    private String eventName;
    private Integer totalAbsences;
}
