package com.example.asm.dao;

import com.example.asm.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account, String> {
    // JpaRepository tự có hàm findById(username) rồi
}