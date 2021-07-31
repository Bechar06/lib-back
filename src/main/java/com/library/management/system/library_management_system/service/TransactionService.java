package com.library.management.system.library_management_system.service;

import com.google.zxing.WriterException;
import com.library.management.system.library_management_system.converter.TransactionConverter;
import com.library.management.system.library_management_system.dto.BillDto;
import com.library.management.system.library_management_system.dto.TransactionDto;
import com.library.management.system.library_management_system.entity.Book;
import com.library.management.system.library_management_system.entity.MemberRecord;
import com.library.management.system.library_management_system.entity.Transaction;
import com.library.management.system.library_management_system.model.LMSException;
import com.library.management.system.library_management_system.repository.BookRepository;
import com.library.management.system.library_management_system.repository.MemberRecordRepository;
import com.library.management.system.library_management_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionConverter transactionConverter;

    @Autowired
    MemberRecordRepository memberRecordRepository;

    @Autowired
    BookRepository bookRepository;

    public TransactionDto findFirst() {
        Transaction transaction = transactionRepository.findFirstByOrderByTransId();
        if (transaction == null) {
            return null;
        } else {
            return transactionConverter.convert(transaction);
        }
    }

    public TransactionDto findByTransactionId(String findByTransCode) {
        Transaction transaction = transactionRepository.findByCodeTrans(findByTransCode);
        if (transaction == null) {
            return null;
        } else {
            return transactionConverter.convert(transaction);
        }
    }

    @Transactional
    public TransactionDto add(TransactionDto transactionDto) throws LMSException, IOException, WriterException {
        if (transactionRepository.existsByCodeTrans(transactionDto.getCodeTrans())) {
            throw new LMSException("Ce code \"code Transaction\" existe déjà");
        }
        Book book = bookRepository.findById(transactionDto.getIdBook()).orElse(null);
        if (book != null && book.getQnt() < transactionDto.getQuantity()) {
            throw new LMSException("Message Qantité");
        }

        if(book != null){
            book.setQnt(book.getQnt() - transactionDto.getQuantity());
        }
        bookRepository.saveAndFlush(book);
        MemberRecord memberRecord = memberRecordRepository.findById(transactionDto.getMemberId()).get();
        Integer bookIssued = memberRecord.getNoBookIssued();
        Transaction transaction = transactionConverter.convert(transactionDto);
        TransactionDto transactionDto1 = transactionConverter.convert(transactionRepository.save(transaction));
        memberRecord.setNoBookIssued(bookIssued + 1);
        memberRecordRepository.save(memberRecord);
        return transactionDto1;
    }

    public void delete(Integer id) throws LMSException {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            transactionRepository.deleteById(id);
        } else {
            throw new LMSException("Cette Transaction ne plus existé");
        }
    }

    public TransactionDto update(TransactionDto transactionDto) throws IOException, LMSException, WriterException {
        Optional<Transaction> transaction = transactionRepository.findById(transactionDto.getTransId());
        if (!transactionDto.getCodeTrans().equals(transaction.get().getCodeTrans())
                && transactionRepository.existsByCodeTrans(transactionDto.getCodeTrans())) {
            throw new LMSException("Ce code \"code Transaction\" existe déjà");
        }
        Transaction transactionDateVlaid = transactionConverter.convert(transactionDto);
        TransactionDto transactionDto1 = transactionConverter.convert(transactionRepository.save(transactionConverter.convert(transactionDto)));
        return transactionDto1;
    }

    public List<TransactionDto> findAll() {
        List<Transaction> transactions = transactionRepository.findByApprovedFalse();
        List<TransactionDto> transactionsDto = new ArrayList<>();
        transactions.forEach(item -> {
            transactionsDto.add(transactionConverter.convert(item));
        });
        return transactionsDto;
    }

    @Transactional
    public List<TransactionDto> approveTransaction(TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findById(transactionDto.getTransId()).orElse(null);
        if (transaction != null) {
            transaction.setApproved(true);
        }
        transactionRepository.saveAndFlush(transaction);
        return findAll();
    }

}
