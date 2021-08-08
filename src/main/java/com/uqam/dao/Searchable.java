package com.uqam.dao;

import com.uqam.model.AppException;
import com.uqam.model.Folder;
import com.uqam.model.User;

public interface Searchable {

    User findByUsernameAndPassword(String username, String password) throws AppException;
    Folder findById(String id) throws AppException;
}
