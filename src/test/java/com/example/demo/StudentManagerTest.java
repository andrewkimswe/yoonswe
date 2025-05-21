package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentManagerTest {
    private StudentManager studentManager;

    @BeforeEach
    public void setUp() {
        studentManager = new StudentManager();
    }

    @Test
    void addStudentTest() {
        String studentName = "홍길동";
        studentManager.addStudent(studentName);

        Assertions.assertTrue(studentManager.hasStudent(studentName));
    }
}
