package com.library.management.system.library_management_system.service;

import com.google.zxing.WriterException;
import com.library.management.system.library_management_system.converter.TransactionConverter;
import com.library.management.system.library_management_system.dto.TransactionDto;
import com.library.management.system.library_management_system.entity.MemberRecord;
import com.library.management.system.library_management_system.entity.Transaction;
import com.library.management.system.library_management_system.model.LMSException;
import com.library.management.system.library_management_system.repository.MemberRecordRepository;
import com.library.management.system.library_management_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionConverter transactionConverter;

    @Autowired
    MemberRecordRepository memberRecordRepository;

    public TransactionDto findFirst() {
        Transaction transaction = transactionRepository.findFirstByOrderByTransId();
        if (transaction == null){
            return null;
        } else {
            return transactionConverter.convert(transaction);
        }
    }

    public TransactionDto findByTransactionId(String findByTransCode){
        Transaction transaction = transactionRepository.findByCodeTrans(findByTransCode);
        if (transaction == null){
            return null;
        } else {
            return transactionConverter.convert(transaction);
        }
    }

    public TransactionDto add(TransactionDto transactionDto) throws LMSException, IOException, WriterException {
        if (transactionRepository.existsByCodeTrans(transactionDto.getCodeTrans())) {
            throw new LMSException("Ce code \"code Transaction\" existe déjà");
        }
        MemberRecord memberRecord=memberRecordRepository.findById(transactionDto.getMemberId()).get();
        Integer maxBookLimit = memberRecord.getMaxBookLimit();
        Integer bookIssued = memberRecord.getNoBookIssued();
        if (bookIssued == maxBookLimit || bookIssued > maxBookLimit ) {
            throw new LMSException("Ce membre a atteint le maximum de livres qu'il peut louer");
        }
        Transaction transaction = transactionConverter.convert(transactionDto);
        if(transaction.getDateOfIssue().isAfter(transaction.getDueDate())){
            throw new LMSException("la date d'expiration doit être supérieure à la date de d'emission");
        }
        TransactionDto transactionDto1 = transactionConverter.convert(transactionRepository.save(transaction));
        memberRecord.setNoBookIssued(bookIssued+1);
        memberRecordRepository.save(memberRecord);
        return transactionDto1;
    }

    public void delete(Integer id) throws LMSException{
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if(transaction.isPresent()){
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
        if(transactionDateVlaid.getDateOfIssue().isAfter(transactionDateVlaid.getDueDate())){
            throw new LMSException("la date d'expiration doit être supérieure à la date de d'emission");
        }
        TransactionDto transactionDto1 = transactionConverter.convert(transactionRepository.save(transactionConverter.convert(transactionDto)));
        return transactionDto1;
    }

    public  List<TransactionDto> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDto> transactionsDto = new ArrayList<>();
        transactions.forEach(item -> {
            transactionsDto.add(transactionConverter.convert(item));
        });
        return transactionsDto;
    }
}
