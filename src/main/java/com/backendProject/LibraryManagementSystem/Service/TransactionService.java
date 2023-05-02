package com.backendProject.LibraryManagementSystem.Service;

import com.backendProject.LibraryManagementSystem.DTO.IssueBookRequestDto;
import com.backendProject.LibraryManagementSystem.DTO.IssueBookResponseDto;
import com.backendProject.LibraryManagementSystem.DTO.WithdrawBookRequestDto;
import com.backendProject.LibraryManagementSystem.DTO.WithdrawBookResponseDto;
import com.backendProject.LibraryManagementSystem.Entity.Book;
import com.backendProject.LibraryManagementSystem.Entity.LibraryCard;
import com.backendProject.LibraryManagementSystem.Entity.Transaction;
import com.backendProject.LibraryManagementSystem.Enum.CardStatus;
import com.backendProject.LibraryManagementSystem.Enum.TransactionStatus;
import com.backendProject.LibraryManagementSystem.Repository.BookRepository;
import com.backendProject.LibraryManagementSystem.Repository.LibraryCardRepository;
import com.backendProject.LibraryManagementSystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    LibraryCardRepository libraryCardRepository;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    TransactionRepository transactionRepository1;

    @Autowired
    LibraryCardRepository cardRepository1;

    @Autowired
    BookRepository bookRepository1;

    @Autowired
    JavaMailSender emailSender1;
    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception{

        //create transaction object
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);

        LibraryCard card;
        try {
            card = libraryCardRepository.findById(issueBookRequestDto.getCardId()).get();
        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid card id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid card id");
        }

        Book book;
        try {
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid book id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid book id");
        }

        // both card and book are valid
        transaction.setBook(book);
        transaction.setCard(card);

        if(card.getCardStatus()!= CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Your card is not activated");
            transactionRepository.save(transaction);
            throw new Exception("Your card is not activated");
        }
        if(book.isIssued()==true){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("this book is already issued");
            transactionRepository.save(transaction);
            throw new Exception("this book is already issued");
        }

        // can issue the book
        book.setIssued(true);
        book.getTransactionList().add(transaction);
        book.setCard(card);
        card.getTransactions().add(transaction);
        card.getBookIssuedList().add(book);

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was successful");

        libraryCardRepository.save(card);

        //create response dto

        IssueBookResponseDto issueBookResponseDto = new IssueBookResponseDto();
        issueBookResponseDto.setTransactionId(transaction.getTransactionNumber());
        issueBookResponseDto.setBookName(book.getTitle());
        issueBookResponseDto.setTransactionStatus(transaction.getTransactionStatus());

        //send an email
        String text = "Congrats !" + card.getStudent().getName()+ "You have been issued "+book.getTitle()+" book.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rucha3702@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Issue Book Notification");
        message.setText(text);
        emailSender.send(message);

        return issueBookResponseDto;
    }
    public WithdrawBookResponseDto withdrawBook(WithdrawBookRequestDto withdrawBookRequestDto) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);
        LibraryCard card;
        try{
            card = cardRepository1.findById(withdrawBookRequestDto.getCardId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid card ID");
            transactionRepository1.save(transaction);
            throw new Exception("Invalid card Id");
        }
        Book book;
        try{
            book = bookRepository1.findById(withdrawBookRequestDto.getBookId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Book ID");
            transactionRepository1.save(transaction);
            throw new Exception("Invalid Book Id");
        }
        if(card.getCardStatus()  != CardStatus.ACTIVATED ){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Your card is not activated");
            transactionRepository1.save(transaction);
            throw new Exception("Your card is not activated");
        }
        if(book.isIssued()==false){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry! Book is not issued...");
            transactionRepository1.save(transaction);
            throw new Exception("Sorry! Book is not issued...");
        }
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was successful");
        transaction.setBook(book);
        transaction.setCard(card);
        transactionRepository1.save(transaction);

        book.setIssued(false);
        book.setCard(card);
        book.getTransactionList().remove(transaction);
        card.getTransactions().remove(transaction);
        card.getBookIssuedList().remove(book);


        cardRepository1.save(card);

        WithdrawBookResponseDto withdrawBookResponseDto = new WithdrawBookResponseDto();
        withdrawBookResponseDto.setTransactionStatus(TransactionStatus.SUCCESS);
        withdrawBookResponseDto.setTransactionId(transaction.getTransactionNumber());
        withdrawBookResponseDto.setBookName(book.getTitle());

        String text = "Congrats !!"+card.getStudent().getName()+"  You has been withdraw "+book.getTitle();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendrahul986@gmail.com ");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Issue Book Notification");
        message.setText(text);
        emailSender.send(message);

        return withdrawBookResponseDto;
    }

}
