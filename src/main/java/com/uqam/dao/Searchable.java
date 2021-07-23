package com.uqam.dao;

import com.uqam.model.User;

public interface Searchable {

    User findByUsernameAndPassword(String username, String password);
}
