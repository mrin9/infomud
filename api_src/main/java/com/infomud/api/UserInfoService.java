package com.infomud.api;

import java.util.List;
import com.infomud.model.*;
import com.google.common.base.Strings;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.infomud.repo.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.context.annotation.Bean;
import com.infomud.model.security.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Service
public class UserInfoService {

  @Autowired
  private UserRepository userRepo;

	public String getLoggedInUserName(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}


	public User getLoggedInUser() {
		String loggedInUserName = this.getLoggedInUserName();
		System.out.format("\n1. Inside >> getLoggedInUser: %s", loggedInUserName);
		User user = this.getUserInfoByUserName(loggedInUserName);
		System.out.format("\n2. After Find User: %s", loggedInUserName);
		return user;
	}

	public User getUserInfoByUserName(String userName){
			User user = this.userRepo.findOneByUserName(userName).orElseGet( () -> new User());
			return user;
	}

	public boolean insertOrSaveUser(User user) {
		this.userRepo.save(user);
		return true;
	}

	public boolean addNewUser(User user) {
		User newUser = this.getUserInfoByUserName(user.getUserName());
		if (newUser.getUserName().equals("new")){
			// This means the username is not found therfore its is returning a default value of "new"
			return this.insertOrSaveUser(user);
		}
		else{
			return false;
		}
	}


}
