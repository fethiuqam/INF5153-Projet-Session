package com.uqam.dao;

import com.uqam.model.AppException;
import com.uqam.model.Folder;

public interface Editable {

    public boolean update(Folder folder) throws AppException;

}
