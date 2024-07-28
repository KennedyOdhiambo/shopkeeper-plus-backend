package com.kennedy.shopkeeper_plus.repositories;

import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByPhoneNumberAndStatus(String phoneNumber, EntityStatus status);

	List<User> findAllByStatus(EntityStatus status);

	Optional<User> findByIdAndStatus(UUID id, EntityStatus status);
}
