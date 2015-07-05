package com.paymee.dao.user;

import com.paymee.domain.user.User;

import java.util.Optional;

public interface UserDao {
	Optional<User> findByUsername(String username);
	User create();
	User update(User user);
	void delete();
}
