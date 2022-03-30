package com.example.demo.student;

import javax.persistence.*;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // TODO: Set up minimum and maximum values
    @NonNull
    private String name;

    @NonNull
    private LocalDate dob;

    @NonNull
    @Column(unique = true, length = 64)
    private String email;

    @Transient
    private Integer age;

    public Integer getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }

}
