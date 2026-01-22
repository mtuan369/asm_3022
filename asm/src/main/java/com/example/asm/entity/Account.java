package com.example.asm.entity;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter; // Dùng cái này
import lombok.NoArgsConstructor;
import lombok.Setter; // Dùng cái này

@SuppressWarnings("serial")
@Getter // Thêm cái này
@Setter // Thêm cái này
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    String username;
    String password;
    String fullname;
    String email;
    String photo;
    Boolean activated;
    Boolean admin;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Order> orders;

    // Giữ nguyên hàm isAdmin() bạn đã thêm thủ công
    public boolean isAdmin() {
        return this.admin != null && this.admin;
    }
}