package com.api.diary.controller;

import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.diary.dto.user.AuthDto;
import com.api.diary.dto.user.AuthResponseDto;
import com.api.diary.dto.user.UserDto;
import com.api.diary.models.User;
import com.api.diary.service.UserService;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {POST})
public class AuthController {
    
    private final UserService userService;

    @PostMapping("/login")  
	public ResponseEntity<AuthResponseDto> login(@RequestBody AuthDto dto) {
		AuthResponseDto response = userService.authenticate(dto);
		return ResponseEntity.ok(response);
}
   
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody UserDto dto) {		
		return new ResponseEntity<>(userService.create(dto), CREATED);		
	}
}
