package com.api.diary.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.web.bind.annotation.RestController;

import com.api.diary.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.api.diary.dto.user.UserDto;
import com.api.diary.models.User;

import lombok.RequiredArgsConstructor;

@Tag(name = "User")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {GET, POST, PUT, DELETE})
public class UserController {
    
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/all/enabled")
    public ResponseEntity<List<User>> findAllEnabled(){
        return ResponseEntity.ok(userService.findAllEnabled());
    }

    @GetMapping("/all/disabled")
    public ResponseEntity<List<User>> findAllDisabled(){
        return ResponseEntity.ok(userService.findAllDisabled());
    }
 
    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestParam("id") Long id,
                                       @RequestParam(value = "newUsername", required = false) String username,
                                       @RequestParam(value = "newEmail", required = false) String email,
                                       @RequestParam(value = "newPassword", required = false) String password){

        UserDto dto = new UserDto(username, email, password);        
        return ResponseEntity.ok(userService.update(dto, id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Delete a user by his ID (Soft Delete -> Enabled = false)")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        if(userService.delete(id)){
            return ResponseEntity.ok("Deleted successful");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{userid}/add-note/{noteid}")
    public ResponseEntity<User> addNote(@PathVariable(value = "userid") Long userId,
                                        @PathVariable(value = "noteid") Long noteId){

        return ResponseEntity.ok(userService.addNote(userId, noteId));
    }

    @PutMapping("/{userid}/remove-note/{noteid}")
    public ResponseEntity<User> removeNote(@PathVariable(value = "userid") Long userId,
                                           @PathVariable(value = "noteid") Long noteId){
                                            
        return ResponseEntity.ok(userService.removeNote(userId, noteId));
    }
    
}
