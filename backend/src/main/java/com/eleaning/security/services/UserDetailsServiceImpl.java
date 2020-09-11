package com.eleaning.security.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eleaning.entity.UserEntity;
import com.eleaning.exception.ResourceNotFoundException;
import com.eleaning.repository.IUserRepository;
import com.eleaning.service.IUserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired 
	private IUserRepository userRepository;
	
	@Autowired
	private IUserService userService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 UserEntity user = userRepository.findByEmail(email)
             	.orElseThrow(() -> 
                     new UsernameNotFoundException("Email Not Found with -> username or email : " + email)
         );
		 return UserPrinciple.build(user);
	}	
	
	@Transactional
    public UserDetails loadUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrinciple.build(user);
    }

}
