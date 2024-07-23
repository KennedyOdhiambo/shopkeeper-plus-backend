package com.kennedy.shopkeeper_plus.users;

import java.time.LocalDateTime;
import java.util.UUID;

import com.kennedy.shopkeeper_plus.Utils.EntityStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "full_name", nullable = false, length = 256)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 256)
    private String phoneNumber;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(name = "business_name", nullable = false, length = 256)
    private String businessName;

    @ManyToOne
    @JoinColumn(name = "business_type_Id")
    private BusinessType businessType;

    @Column(name = "business_location", length = 256)
    private String businessLocation;

    @Column(name = "date_joined")
    private LocalDateTime dateJoined;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar default 'ACTIVE'")
    private EntityStatus status = EntityStatus.ACTIVE;

}
