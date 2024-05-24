package com.springTest.CRUDApplication.repo;

import com.springTest.CRUDApplication.model.book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface bookRepo extends JpaRepository<book, Long> {

}
