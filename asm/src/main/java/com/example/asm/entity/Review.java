package com.example.asm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer rating;
    String comment;

    @Temporal(TemporalType.DATE)
    Date reviewDate = new Date();

    @ManyToOne
    @JoinColumn(name = "ProductId")
    Product product;

    @ManyToOne
    @JoinColumn(name = "Username")
    Account account;
}