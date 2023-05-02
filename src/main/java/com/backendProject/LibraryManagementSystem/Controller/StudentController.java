package com.backendProject.LibraryManagementSystem.Controller;

import com.backendProject.LibraryManagementSystem.DTO.StudentRequestDto;
import com.backendProject.LibraryManagementSystem.DTO.StudentResponseDto;
import com.backendProject.LibraryManagementSystem.DTO.StudentUpdateEmailRequestDto;
import com.backendProject.LibraryManagementSystem.Entity.Student;
import com.backendProject.LibraryManagementSystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public StudentResponseDto addStudent(@RequestBody StudentRequestDto studentRequestDto){
       try {
           return studentService.addStudent(studentRequestDto);
       }catch (Exception e){
           throw new RuntimeException("Invalid Statement" +e.getMessage());
       }
    }

    @GetMapping("find_by_id")
    public String getStudentById(@RequestParam("id") int id){
        try {
            return studentService.findById(id);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/find_by_email")
    public String findStudentByEmail(@RequestParam("email") String email){
       try {
           return studentService.findByEmail(email);
       }catch (Exception e){
           throw new RuntimeException(e.getMessage());
       }
    }

    @GetMapping("/find_by_age")
    public List<Student> findStudentByAge(@RequestParam("age") int age){
        return studentService.findByAge(age);
    }

    @PutMapping("/update_email")
        public StudentResponseDto updateEmail(@RequestBody StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
            return studentService.updateEmail(studentUpdateEmailRequestDto);
        }

}
