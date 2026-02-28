package com.senyitte.accountservices.models;

import com.senyitte.accountservices.customer.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String accountNumber;
    private Double balance = 0.0;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    private Long customerId;
    @Transient
    private Customer customer;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
