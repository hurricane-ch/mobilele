package org.softuni.mobilele.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.softuni.mobilele.model.dto.UserRegistrationDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

    public void registerUser (UserRegistrationDTO userRegistrationDTO) {
        System.out.println(userRegistrationDTO);
    }


}
