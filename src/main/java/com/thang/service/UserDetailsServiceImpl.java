package com.thang.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thang.model.User;
import com.thang.repository.IUserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// đầu tiên mình query xuống database xem có user đó không
		User user = userRepository.findByEmail(username);
		System.out.println(user);
		if (user == null) {
			throw new UsernameNotFoundException("user name not found");
		}
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		String role = user.getRole().getName();
		authorities.add(new SimpleGrantedAuthority(role));
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}

}
