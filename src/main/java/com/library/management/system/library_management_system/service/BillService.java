package com.library.management.system.library_management_system.service;

import com.library.management.system.library_management_system.converter.BillConverter;
import com.library.management.system.library_management_system.converter.BookConverter;
import com.library.management.system.library_management_system.dto.BillDto;
import com.library.management.system.library_management_system.dto.BookDto;
import com.library.management.system.library_management_system.entity.Bill;
import com.library.management.system.library_management_system.entity.Book;
import com.library.management.system.library_management_system.entity.MemberRecord;
import com.library.management.system.library_management_system.entity.Transaction;
import com.library.management.system.library_management_system.jasper.report.ReportService;
import com.library.management.system.library_management_system.model.LMSException;
import com.library.management.system.library_management_system.repository.BillRepository;
import com.library.management.system.library_management_system.repository.MemberRecordRepository;
import com.library.management.system.library_management_system.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BillService {

    @Autowired
    BillRepository billRepository;

    @Autowired
    BillConverter billConverter;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MemberRecordRepository memberRecordRepository;

    @Autowired
    BookConverter bookConverter;

    private final ReportService reportService;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Transactional
    public BillDto add(BillDto billDto) throws LMSException {
        if (billRepository.existsByCodeBill(billDto.getCodeBill())) {
            throw new LMSException("Ce code \"code Facture\" existe déjà");
        }
        Bill bill = billConverter.convert(billDto);
        billRepository.save(bill);
        transactionRepository.updatePayedStatus(bill.getMemberRecordId().getMemberRecordId());
        return billConverter.convert(bill);
    }

    private BillDto fillTheBillDto(BillDto billDto) {
        return null;
    }

    public BillDto findFirst() {
        Bill bill = billRepository.findFirstByOrderByBillId();
        if (bill == null) {
            return null;
        } else {
            return billConverter.convert(bill);
        }
    }
    public void delete(Integer id) throws LMSException, IOException {
        Optional<Bill> bill = billRepository.findById(id);
        try {
            if (bill.isPresent()) {
                billRepository.deleteById(id);
            } else {
                throw new LMSException("Cette facture ne plus existé");
            }
        } catch (Exception e) {
            throw new LMSException("Cette facture est déjà loué par un client.\n pour supprimer ce livre, la transaction doit être terminée");
        }
    }

    public BillDto update(BillDto billDto) throws LMSException {
        Optional<Bill> bill = billRepository.findById(billDto.getBillId());
        if (!bill.get().getCodeBill().equals(billDto.getCodeBill()) && billRepository.existsByCodeBill(billDto.getCodeBill())) {
            throw new LMSException("Ce code \"code Livre\" existe déjà");
        }
        return billConverter.convert(billRepository.save(billConverter.convert(billDto)));
    }

    public List<BookDto> fillDataSetForReport(Integer memberId) {
    	MemberRecord id = memberRecordRepository.findById(memberId).get();
        List<Transaction> transactions = transactionRepository.findByMemberRecordId(id);
        List<Book> books= new ArrayList<>();
        transactions.forEach( trans -> {
            books.add(trans.getBookId());
        });
        List<BookDto> bookDtos = bookConverter.convertAllToDto(books);
        return bookDtos;
    }

    public byte[] getBytes(@PathVariable Integer billId) {
        Map<String, Object> params = new HashMap<>();
        Optional<Bill> bill = billRepository.findById(billId);
        String date = bill.get().getDate().format(formatter);
        List<BookDto> bookDtos = fillDataSetForReport(bill.get().getMemberRecordId().getMemberRecordId());
        MemberRecord memberRecord = bill.get().getMemberRecordId();
        params.put("fullName", memberRecord.getFullName());
        params.put("adress", memberRecord.getAdress());
        params.put("phoneNo", memberRecord.getPhoneNo());
        params.put("date", date);
        params.put("codeMemberRecord", memberRecord.getCodeMemberRecord());
        params.put("billCode", bill.get().getCodeBill());
        JRDataSource bookDataSource = new JRBeanCollectionDataSource(bookDtos);
        return reportService.generatePDFReport("facture", params, bookDataSource);
    }

    public List<BillDto> findAll() {
        List<Bill> bills = billRepository.findAll();
        List<BillDto> billsDto = new ArrayList<>();
        bills.forEach(item -> {
            billsDto.add(billConverter.convert(item));
        });
        return billsDto;
    }
}
