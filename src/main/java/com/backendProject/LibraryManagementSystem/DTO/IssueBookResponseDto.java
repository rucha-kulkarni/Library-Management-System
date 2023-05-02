package com.backendProject.LibraryManagementSystem.DTO;

import com.backendProject.LibraryManagementSystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssueBookResponseDto {

    private String transactionId;

    private String bookName;

    private TransactionStatus transactionStatus;

}
