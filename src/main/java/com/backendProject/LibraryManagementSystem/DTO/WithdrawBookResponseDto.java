package com.backendProject.LibraryManagementSystem.DTO;

import com.backendProject.LibraryManagementSystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WithdrawBookResponseDto {
    private String transactionId;
    private String bookName;
    private TransactionStatus transactionStatus;
}
