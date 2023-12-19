package com.WeComply.WeComplyBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "max_year")
    private Integer maxYear;

    @Column(name = "dept_id")
    private Integer departmentId;

    @ManyToOne
    @JoinColumn(name = "dept_id", referencedColumnName = "dept_id", insertable = false, updatable = false)
    private Department department;

}
