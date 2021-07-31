package com.library.management.system.library_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.library.management.system.library_management_system.entity.MemberRecord;
import com.library.management.system.library_management_system.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findFirstByOrderByTransId();

    Transaction findByCodeTrans(String codeTrans);

    boolean existsByCodeTrans(String codeTrans);
    @Transactional
    @Modifying
    @Query("update Transaction set payed=true where transId=:id")
    void updatePayedStatus(@Param("id") Integer id);

    @Query("select count(tr.transId) from Transaction tr where tr.memberRecordId.memberRecordId=:memberId and tr.approved = false")
    Integer numberOfCurrentTransaction(@Param("memberId") Integer memberId);

	List<Transaction> findByMemberRecordId(MemberRecord memberId);

    List<Transaction> findByTransIdAndMemberRecordId(Integer transId, MemberRecord memberId);

	List<Transaction> findByApprovedFalse();
    void deleteAllByMemberRecordId(MemberRecord memberId);
}
