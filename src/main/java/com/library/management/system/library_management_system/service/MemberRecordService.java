package com.library.management.system.library_management_system.service;

import com.google.zxing.WriterException;
import com.library.management.system.library_management_system.converter.MemberRecordConverter;
import com.library.management.system.library_management_system.dto.MemberRecordDto;
import com.library.management.system.library_management_system.entity.MemberRecord;
import com.library.management.system.library_management_system.model.LMSException;
import com.library.management.system.library_management_system.model.QRCodeGenerator;
import com.library.management.system.library_management_system.repository.MemberRecordRepository;
import com.library.management.system.library_management_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberRecordService {

    @Autowired
    MemberRecordRepository memberRecordRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MemberRecordConverter memberRecordConverter;


    public MemberRecordDto findFirst() {
        MemberRecord memberRecord = memberRecordRepository.findFirstByOrderByMemberRecordId();
        if (memberRecord == null) {
            return null;
        } else {
            return memberRecordConverter.convert(memberRecord);
        }
    }

    public MemberRecordDto findByCodeMemberRecord(String Code) {
        MemberRecord memberRecord = memberRecordRepository.findByCodeMemberRecord(Code);
        if (memberRecord == null) {
            return null;
        } else {
            return memberRecordConverter.convert(memberRecord);
        }
    }

    public MemberRecordDto add(MemberRecordDto memberRecordDto) throws LMSException, IOException, WriterException {
        if (memberRecordRepository.existsByCodeMemberRecord(memberRecordDto.getCodeMemberRecord())) {
            throw new LMSException("Ce code \"code Livre\" existe déjà");
        }
        MemberRecordDto memberRecordDto1 = memberRecordConverter.convert(memberRecordRepository.save(memberRecordConverter.convert(memberRecordDto)));
        return memberRecordDto1;
    }

    public void delete(Integer id) throws LMSException, IOException {
        Optional<MemberRecord> memberRecord = memberRecordRepository.findById(id);
        if (memberRecord.isPresent()) {
            Integer transactionInProgress =transactionRepository.numberOfCurrentTransaction(id);
            if(transactionInProgress>0){
                throw new LMSException("vous ne pouvez pas supprimer ce membre parce qu'il a une transaction en cours");
            }
            transactionRepository.deleteAllByMemberRecordId(id);
            memberRecordRepository.deleteById(id);
            Files.deleteIfExists(Paths.get(QRCodeGenerator.QR_CODE_IMAGE_PATH + memberRecord.get().getCodeMemberRecord() + ".png"));
        } else {
            throw new LMSException("Ce livre ne plus existé");
        }
    }

    public MemberRecordDto update(MemberRecordDto memberRecordDto) throws IOException, LMSException, WriterException {
        Optional<MemberRecord> memberRecord = memberRecordRepository.findById(memberRecordDto.getMemberRecordId());
        if (!memberRecord.get().getCodeMemberRecord().equals(memberRecordDto.getCodeMemberRecord()) && memberRecordRepository.existsByCodeMemberRecord(memberRecordDto.getCodeMemberRecord())) {
            throw new LMSException("Ce code \"code membre\" existe déjà");
        }
        String oldMemberRecordCode = memberRecord.get().getCodeMemberRecord();
        MemberRecordDto memberRecordToReturn = memberRecordConverter.convert(memberRecordRepository.save(memberRecordConverter.convert(memberRecordDto)));
        Files.deleteIfExists(Paths.get(QRCodeGenerator.QR_CODE_IMAGE_PATH + oldMemberRecordCode + ".png"));
        return memberRecordToReturn;
    }

    public List<MemberRecordDto> findAll() {
        List<MemberRecord> memberRecords = memberRecordRepository.findAll();
        List<MemberRecordDto> memberRecordsDto = new ArrayList<>();
        memberRecords.forEach(item -> {
            memberRecordsDto.add(memberRecordConverter.convert(item));
        });
        return memberRecordsDto;
    }

}
