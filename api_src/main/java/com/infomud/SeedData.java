package com.infomud;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.infomud.api.UserInfoService;
// User Model and Repo
import com.infomud.model.security.User;
import com.infomud.model.security.Role;
import com.infomud.repo.UserRepository;

import java.util.*;

@Service
public final class SeedData {

	@Autowired private UserInfoService userInfoService;

	//Users
	public void insertDefaultUsers() {
		System.out.println("[ *** Mrin *** ]: Adding Users");
		this.addUser("admin"  , "admin");
		this.addUser("user"   , "user");
		this.addUser("demo"   , "demo");
		this.addUser("inactive", "inactive");
		this.addUser("nitinsw", "pwd4dev");
	}

	private void addUser(String username, String password) {
		Role role = username.equals("admin") ? Role.ADMIN: Role.USER;
		boolean isPendingActivation = username.equals("inactive") ? true: false;
		User user = new User(username, password, role , username, username, isPendingActivation );
		userInfoService.insertOrSaveUser(user);
	}

}
