package com.example.asm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "Favorites", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"Username", "ProductId"}) // 1 người chỉ like 1 SP 1 lần
})
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne @JoinColumn(name = "Username")
    Account account;

    @ManyToOne @JoinColumn(name = "ProductId")
    Product product;

    @Temporal(TemporalType.DATE)
    Date likeDate = new Date();
}