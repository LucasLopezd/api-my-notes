package com.api.diary.util.service;

import com.api.diary.dto.user.AuthDto;
import com.api.diary.dto.user.AuthResponseDto;
import com.api.diary.dto.user.UserDto;
import com.api.diary.models.User;

public interface UserServiceUtils extends BasicCrud<UserDto, User>{
    
    boolean checkIfEmailAlreadyExists(String email);

    boolean existsById(Long id);

    AuthResponseDto authenticate(AuthDto dto);

    User addNote(Long userId, Long noteId);

    User removeNote(Long userId, Long noteId);
}
