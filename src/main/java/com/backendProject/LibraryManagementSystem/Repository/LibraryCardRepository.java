package com.backendProject.LibraryManagementSystem.Repository;

import com.backendProject.LibraryManagementSystem.Entity.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryCardRepository extends JpaRepository<LibraryCard,Integer> {
}
