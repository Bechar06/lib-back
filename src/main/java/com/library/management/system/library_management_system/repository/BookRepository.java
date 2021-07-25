package com.library.management.system.library_management_system.repository;

import com.library.management.system.library_management_system.entity.Book;
import com.library.management.system.library_management_system.entity.MemberRecord;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findFirstByOrderByBookId();
    Book findByCodeBook(String codeBook);
    boolean existsByCodeBook(String codeBook);

	//Optional<Book> findById(Book bookId);

}
