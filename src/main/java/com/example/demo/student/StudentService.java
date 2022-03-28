package com.example.demo.student;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {

    public List<Student> getStudents(){
        return List.of(
                new Student(1L, "Sas", 18, LocalDate.of(2000, Month.JANUARY, 5), "sas@mail.com"),
                new Student(1L, "Kyle", 18, LocalDate.of(2000, Month.JANUARY, 5), "kyle@mail.com")
        );
    }
}
