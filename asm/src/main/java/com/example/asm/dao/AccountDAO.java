package com.example.asm.dao;

import com.example.asm.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountDAO extends JpaRepository<Account, String> {
    // Thêm dòng này để tìm user theo email
    @Query("SELECT a FROM Account a WHERE a.email = ?1")
    Account findByEmail(String email);
}