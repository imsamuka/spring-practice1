package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        emailAvailableOrException(student.getEmail());
        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        // TODO: throw NotFound Exception

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id '" + studentId + "' not found"));

        if (name != null && name.length() > 0 && !name.equals(student.getName()))
            student.setName(name);

        if (email != null && email.length() > 0 && !email.equals(student.getEmail())) {
            emailAvailableOrException(email);
            student.setEmail(email);
        }
    }

    public void removeStudent(Long studentId) {
        // TODO: throw NotFound Exception
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalStateException("Student with id '" + studentId + "' not found");
        }

        studentRepository.deleteById(studentId);
    }

    private void emailAvailableOrException(String email) {
        if (studentRepository.existsByEmail(email))
            throw new IllegalStateException("Email already taken");
    }
}
