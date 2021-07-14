package com.library.management.system.library_management_system.repository;

import com.library.management.system.library_management_system.entity.MemberRecord;
import com.library.management.system.library_management_system.entity.MemberRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRecordRepository extends JpaRepository<MemberRecord, Integer> {

    MemberRecord findFirstByOrderByMemberRecordId();

    MemberRecord findByCodeMemberRecord(String codeMemberRecord);

    boolean existsByCodeMemberRecord(String codeMemberRecord);
}
