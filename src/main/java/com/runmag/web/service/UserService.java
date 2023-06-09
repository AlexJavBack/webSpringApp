package com.runmag.web.service;

import com.runmag.web.dto.RegistrationDto;
import com.runmag.web.models.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);

    User findByUserName(String userName);
}
