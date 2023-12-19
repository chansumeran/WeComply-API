package com.WeComply.WeComplyBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Integer departmentId;

    @Column(name = "dept_code")
    private String departmentCode;

    @Column(name = "dept_name")
    private String departmentName;
}