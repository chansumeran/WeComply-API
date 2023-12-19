package com.WeComply.WeComplyBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_initial")
    private String middleInitial;

    private String sex;

    @Column(name = "year_level")
    private Integer yearLevel;

    @Column(name = "is_officer")
    private Boolean isOfficer;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "sanction_id")
    private Integer sanctionId;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false, nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "sanction_id", referencedColumnName = "sanction_id", insertable = false, updatable = false)
    private Sanction sanction;

}
