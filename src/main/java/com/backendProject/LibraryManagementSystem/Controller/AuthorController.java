package com.backendProject.LibraryManagementSystem.Controller;

import com.backendProject.LibraryManagementSystem.DTO.AuthorRequestDto;
import com.backendProject.LibraryManagementSystem.DTO.AuthorResponseDto;
import com.backendProject.LibraryManagementSystem.Entity.Author;
import com.backendProject.LibraryManagementSystem.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public AuthorResponseDto addAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        return authorService.addAuthor(authorRequestDto);
    }

    @GetMapping("/get_authors")
    public List<Author> getAuthors(){
        return authorService.getAuthors();
    }
}
