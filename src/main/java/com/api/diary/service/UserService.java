package com.api.diary.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.diary.dto.user.AuthDto;
import com.api.diary.dto.user.AuthResponseDto;
import com.api.diary.dto.user.UserDto;
import com.api.diary.enums.Role;
import com.api.diary.exception.BadRequestException;
import com.api.diary.exception.EmailAlreadyInUseException;
import com.api.diary.exception.NotFoundException;
import com.api.diary.mapper.GenericModelMapper;
import com.api.diary.models.Note;
import com.api.diary.models.User;
import com.api.diary.repository.NoteRepository;
import com.api.diary.repository.UserRepository;
import com.api.diary.security.jwt.CustomAuthenticatorManager;
import com.api.diary.security.jwt.CustomUserDetailsService;
import com.api.diary.security.jwt.JwtUtils;
import com.api.diary.util.Utils;
import com.api.diary.util.service.UserServiceUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceUtils, Serializable {

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final GenericModelMapper mapper;
    private final BCryptPasswordEncoder encoder;
    private final CustomAuthenticatorManager authenticatorManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public User create(UserDto dto) {
        if (checkIfEmailAlreadyExists(dto.getUserEmail())) {
            throw new EmailAlreadyInUseException(
                    UserService.class.getSimpleName() + " / " + UserService.class.getPackageName());
        }

        User user = mapper.map(dto, User.class);

        user.setRole((userRepository.findAll().isEmpty()) ? Role.ADMIN : Role.USER);
        user.setPassword(encoder.encode(dto.getUserPassword()));
        user.setEnabled(true);
        user.setNotes(Collections.emptyList());

        return userRepository.save(user);
    }

    @Override
    public User update(UserDto dto, Long id) {
        if (checkIfEmailAlreadyExists(dto.getUserEmail())) {
            throw new EmailAlreadyInUseException(
                    UserService.class.getSimpleName() + " / " + UserService.class.getPackageName());
        }
        User user = checkAndSetAtributes(dto, id);
        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        if (existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<User> findAllEnabled() {
        return userRepository.findByEnabledTrue();
    }

    @Override
    public List<User> findAllDisabled() {
        return userRepository.findByEnabledFalse();
    }

    @Override
    public boolean checkIfEmailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User checkAndSetAtributes(UserDto dto, Long id) {
        User user = findById(id);

        if (dto.getUserName() != null) {
            user.setUsername(dto.getUserName());
        }
        if (dto.getUserEmail() != null) {
            user.setEmail(dto.getUserEmail());
        }
        if (dto.getUserPassword() != null) {
            user.setPassword(encoder.encode(dto.getUserPassword()));
        }
        return user;
    }

    @Override
    public AuthResponseDto authenticate(AuthDto dto) {

        final Authentication authentication = authenticatorManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated()) {

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
            final String jwt = jwtUtils.generateToken(userDetails);

            return new AuthResponseDto(jwt);
        } else {
            throw new NotFoundException("Login error, please check the data entered "
                    + UserService.class.getSimpleName() + " / " + UserService.class.getPackageName());
        }
    }

    @Override
    public User addNote(Long userId, Long noteId) {
        if (!Utils.checkParameters(userId) && !Utils.checkParameters(noteId)) {
            throw new BadRequestException(
                    "IDs can't be negative value or null. (user ID = " + userId + "), (note ID = " + noteId + " ),");
        }

        Note note = noteRepository.findById(noteId).orElseThrow();
        User user = findById(userId);

        user.getNotes().add(note);

        return userRepository.save(user);
    }

    @Override
    public User removeNote(Long userId, Long noteId) {
        if (!Utils.checkParameters(userId) && !Utils.checkParameters(noteId)) {
            throw new BadRequestException(
                    "IDs can't be negative value or null. (user ID = " + userId + "), (note ID = " + noteId + " ),");
        }

        User user = findById(userId);

        user.getNotes().removeIf(note -> note.getId() == noteId.intValue());
        return userRepository.save(user);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}
