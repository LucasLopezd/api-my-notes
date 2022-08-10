package com.api.diary.service.timer;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.diary.models.Note;
import com.api.diary.models.User;
import com.api.diary.repository.NoteRepository;
import com.api.diary.repository.UserRepository;
import com.api.diary.service.NoteService;
import com.api.diary.service.UserService;
import com.api.diary.util.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimerService implements Serializable{

    private final UserService userService;
    private final UserRepository userRepository;
    private final NoteService noteService;
    private final NoteRepository noteRepository;

    @Transactional
    public void refreshDataBase(){
        List<User> users = userService.findAll();
        List<Note> notes = noteService.findAll();

        users.forEach(user -> user.setNotes(Utils.refreshNotesDaysRemaining(user.getNotes())));
        notes.forEach(note -> note = Utils.refreshNoteDaysRemaining(note));

        noteRepository.saveAll(notes);
        userRepository.saveAll(users);
    }
    
}
