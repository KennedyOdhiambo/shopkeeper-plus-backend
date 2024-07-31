package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.repositories.UserRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	private final UserRepository userRepository;

	public UserDetailsServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				       .orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
}
