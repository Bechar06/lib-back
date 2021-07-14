package com.library.management.system.library_management_system.converter;

import com.library.management.system.library_management_system.dto.BillDto;
import com.library.management.system.library_management_system.entity.Bill;
import com.library.management.system.library_management_system.repository.MemberRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillConverter extends Converter<Bill, BillDto> {

    @Autowired
    MemberRecordRepository memberRecordRepository;

    @Override
    public Bill convert(BillDto source) {
        Bill bill = super.convert(source);
        if (source != null)
            bill.setMemberRecordId(memberRecordRepository.findById(source.getMemberId()).get());
        return bill;
    }

    @Override
    public BillDto convert(Bill source) {
        BillDto billDto = super.convert(source);
        if(source != null)
            billDto.setMemberId(source.getMemberRecordId().getMemberRecordId());
            billDto.setMemberCode(source.getMemberRecordId().getCodeMemberRecord());
        return billDto;
    }
}
