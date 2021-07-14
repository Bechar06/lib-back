package com.library.management.system.library_management_system.repository;

import com.library.management.system.library_management_system.entity.Book;
import com.library.management.system.library_management_system.entity.MemberRecord;
import com.library.management.system.library_management_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findFirstByOrderByTransId();

    Transaction findByCodeTrans(String codeTrans);

    boolean existsByCodeTrans(String codeTrans);

    @Transactional
    @Modifying
    @Query("update Transaction set payed=true where memberRecordId.memberRecordId=:memberId")
    void updatePayedStatus(@Param("memberId") Integer memberId);

    @Query("select count(tr.transId) from Transaction tr where tr.memberRecordId.memberRecordId=:memberId and current_date > tr.dueDate")
    Integer numberOfCurrentTransaction(@Param("memberId") Integer memberId);

    List<Transaction> findByMemberRecordId(MemberRecord memberId);


    void deleteAllByMemberRecordId(Integer memberId);
}
