package com.backendProject.LibraryManagementSystem.Repository;

import com.backendProject.LibraryManagementSystem.Entity.Student;
import org.hibernate.cfg.JPAIndexHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    //custom queries by attribute
    Student findByEmail(String email);

    List<Student> findByAge(int age);
}
