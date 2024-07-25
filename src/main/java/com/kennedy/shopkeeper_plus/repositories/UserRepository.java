package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByPhoneNumber(String phoneNumber);
}
