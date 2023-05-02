package com.backendProject.LibraryManagementSystem.Service;

import com.backendProject.LibraryManagementSystem.DTO.StudentRequestDto;
import com.backendProject.LibraryManagementSystem.DTO.StudentResponseDto;
import com.backendProject.LibraryManagementSystem.DTO.StudentUpdateEmailRequestDto;
import com.backendProject.LibraryManagementSystem.Entity.LibraryCard;
import com.backendProject.LibraryManagementSystem.Entity.Student;
import com.backendProject.LibraryManagementSystem.Enum.CardStatus;
import com.backendProject.LibraryManagementSystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto){

        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setEmail(studentRequestDto.getEmail());
        student.setDepartment(studentRequestDto.getDepartment());

        //create a card object
        LibraryCard card = new LibraryCard();
        card.setCardStatus(CardStatus.ACTIVATED);

        student.setCard(card);
        card.setStudent(student);

        studentRepository.save(student);

        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setName(student.getName());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setId(student.getId());

        return studentResponseDto;
    }
    public String findById(int id) {
        Student student = studentRepository.findById(id).get();
        return student.getName();
    }
    public String findByEmail(String email){
        Student student = studentRepository.findByEmail(email);
        return student.getName();
    }

    public List<Student> findByAge(int age){
        List<Student> studentListByAge = studentRepository.findByAge(age);
        return studentListByAge;
    }

    public StudentResponseDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){

        Student student = studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
        student.setEmail(studentUpdateEmailRequestDto.getEmail());

        //update step
        Student updatedStudent = studentRepository.save(student);

        // convert updated student to response dto
        StudentResponseDto studentResponseDto= new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getId());
        studentResponseDto.setName(updatedStudent.getName());
        studentResponseDto.setEmail(updatedStudent.getEmail());

        return studentResponseDto;
    }

}
