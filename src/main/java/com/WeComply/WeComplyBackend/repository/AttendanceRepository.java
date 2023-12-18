package com.WeComply.WeComplyBackend.repository;

import com.WeComply.WeComplyBackend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByStudentId(Integer studentId);

    @Query("SELECT SUM(a.totalAbsences) FROM Attendance a WHERE a.studentId = :studentId")
    Integer calculateOverall(Integer studentId);
}
