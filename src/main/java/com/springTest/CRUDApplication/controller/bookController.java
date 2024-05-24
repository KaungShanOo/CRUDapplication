package com.springTest.CRUDApplication.controller;

import com.springTest.CRUDApplication.model.book;
import com.springTest.CRUDApplication.repo.bookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class bookController {
    @Autowired
    private bookRepo bookRepo;

    @GetMapping("/books")
    public ResponseEntity<List<book>> getAllBooks(){
        try {
            List<book> bookList = new ArrayList<>();
            bookRepo.findAll().forEach(bookList::add);

            if(bookList.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(bookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<book> getBookById(@PathVariable Long id){
        Optional<book> bookData = bookRepo.findById(id);

        if (bookData.isPresent()) {
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBook")
    public ResponseEntity<book> addBook(@RequestBody book book){
        book bookObj= bookRepo.save(book);

        return new ResponseEntity<>(bookObj, HttpStatus.OK);
    }

    @DeleteMapping("/removeBookById/{id}")
    public ResponseEntity<HttpStatus> removeBookById(@PathVariable Long id){
        bookRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<book> updateBookById(@PathVariable Long id, @RequestBody book newBookData){

       Optional<book> oldBookData = bookRepo.findById(id);

       if (oldBookData.isPresent()) {
           book updatedBook= oldBookData.get();
           updatedBook.setTitle(newBookData.getTitle());
           updatedBook.setAuthor(newBookData.getAuthor());
           book bookObj= bookRepo.save(updatedBook);
           return new ResponseEntity<>(bookObj,HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
