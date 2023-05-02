package com.backendProject.LibraryManagementSystem.Service;

import com.backendProject.LibraryManagementSystem.DTO.AuthorRequestDto;
import com.backendProject.LibraryManagementSystem.DTO.AuthorResponseDto;
import com.backendProject.LibraryManagementSystem.Entity.Author;
import com.backendProject.LibraryManagementSystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto){
        Author author = new Author();

        author.setName(authorRequestDto.getName());
        author.setAge(authorRequestDto.getAge());
        author.setMobNo(authorRequestDto.getMobNo());
        author.setEmail(authorRequestDto.getEmail());
        authorRepository.save(author);

        AuthorResponseDto authorResponseDto = new AuthorResponseDto();
        authorResponseDto.setId(author.getId());
        authorResponseDto.setName(author.getName());
        authorResponseDto.setEmail(author.getEmail());

        return authorResponseDto;
    }

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }
}
