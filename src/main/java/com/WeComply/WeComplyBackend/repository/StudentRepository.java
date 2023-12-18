package com.WeComply.WeComplyBackend.repository;

import com.WeComply.WeComplyBackend.dto.StudentResponse;
import com.WeComply.WeComplyBackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "SELECT DISTINCT s.* FROM student s " +
            "JOIN department d ON s.dept_code = d.dept_code " +
            "LEFT JOIN attendance a ON s.student_id = a.student_id " +
            "LEFT JOIN event e ON a.event_id = e.event_id " +
            "WHERE (:departmentCode IS NULL OR d.dept_code = :departmentCode) " +
            "AND (:course IS NULL OR s.course = :course) " +
            "AND (:yearLevel IS NULL OR s.year_level = :yearLevel) " +
            "AND (:eventName IS NULL OR e.event_name = :eventName)", nativeQuery = true)
    List<Student> findByDynamicFilters(@Param("departmentCode") String deptCode,
                                       @Param("course") String course,
                                       @Param("yearLevel") Integer yearLevel,
                                       @Param("eventName") String eventCode);

}
