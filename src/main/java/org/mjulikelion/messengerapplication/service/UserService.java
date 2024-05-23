package org.mjulikelion.messengerapplication.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.messengerapplication.authentication.PasswordHashEncryption;
import org.mjulikelion.messengerapplication.domain.User;
import org.mjulikelion.messengerapplication.dto.request.user.LoginRequest;
import org.mjulikelion.messengerapplication.dto.request.user.UserModifyRequest;
import org.mjulikelion.messengerapplication.dto.request.user.UserRegisterRequest;
import org.mjulikelion.messengerapplication.exception.AuthorizeException;
import org.mjulikelion.messengerapplication.exception.ConflictException;
import org.mjulikelion.messengerapplication.exception.NotFoundException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.mjulikelion.messengerapplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordHashEncryption passwordHashEncryption;

    public void register(UserRegisterRequest userRegisterRequest){

        if(userRepository.existsByEmail(userRegisterRequest.getEmail())){
            throw new ConflictException(ErrorCode.USER_CONFLICT);
        }


        User user = User.builder()
                .email(userRegisterRequest.getEmail())
                .password(passwordHashEncryption.encrypt(userRegisterRequest.getPassword()))
                .name(userRegisterRequest.getName())
                .build();

        userRepository.save(user);
    }

    public UUID login(LoginRequest loginRequest){
        User user = findUserByEmail(loginRequest.getEmail());
        if(!passwordHashEncryption.matches(loginRequest.getPassword(), user.getPassword())){
            throw new AuthorizeException(ErrorCode.USER_UNAUTHORIZED);
        }
        return user.getId();
    }

    public void modify(UserModifyRequest userModifyRequest, User user){
        user.changeName(userModifyRequest.getName());
        userRepository.save(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    private User findUserByEmail(String email){
        return  userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

}
