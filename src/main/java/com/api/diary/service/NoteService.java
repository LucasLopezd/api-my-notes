package com.api.diary.service;

import java.time.LocalDate;
import java.util.List;


import java.io.Serializable;
import org.springframework.stereotype.Service;
import static java.time.temporal.ChronoUnit.DAYS;

import com.api.diary.dto.note.NoteDto;
import com.api.diary.exception.DateExpiredException;
import com.api.diary.mapper.GenericModelMapper;
import com.api.diary.models.Note;
import com.api.diary.repository.NoteRepository;
import static com.api.diary.util.Utils.refreshNoteDaysRemaining;
import static com.api.diary.util.Utils.refreshNotesDaysRemaining;
import com.api.diary.util.service.NoteServiceUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService implements NoteServiceUtils, Comparable<String>, Serializable{
    
    private final NoteRepository noteRepository; 
    private final GenericModelMapper mapper;

    @Override
    public Note create(NoteDto dto) {
        Note note = mapper.map(dto, Note.class);

        note.setExpired(false);
        note.setDescription((note.getDescription().isEmpty()) ? "empty" : dto.getNoteDescription());
        note.setCreationDate(LocalDate.now());
        note.setDaysRemaining(getDaysRemaining(note.getExpirationDate()));
        
        return noteRepository.save(note);
    }

    @Override
    public Note update(NoteDto dto, Long id) {
        Note note = checkAndSetAtributes(dto, id);
        return noteRepository.save(note);
    }

    @Override
    public boolean delete(Long id) {
        if(existsById(id)){
            noteRepository.deleteById(id);
            return true;
        }   
        return false;     
    }

    @Override
    public List<Note> findAll() {
        List<Note> notes = noteRepository.findAll();
        return refreshNotesDaysRemaining(notes);
    }

    @Override
    public Note findById(Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        return refreshNoteDaysRemaining(note);
    }
        

    @Override
    public List<Note> findAllEnabled() {
        return noteRepository.findByExpiredTrue();
    }

    @Override
    public List<Note> findAllDisabled() {
        return noteRepository.findByExpiredFalse();
    }
    
    @Override
    public Note checkAndSetAtributes(NoteDto dto, Long id) {
        Note note = findById(id);

        if(!dto.getNoteDescription().isEmpty()){
            note.setDescription(dto.getNoteDescription());
        }
        if (dto.getNotePrice() != null) {
            note.setPrice(dto.getNotePrice());
        }
        
        return note;
    }

    @Override
    public int getDaysRemaining(LocalDate expirationDate) {
        LocalDate now = LocalDate.now();
        if(expirationDate.isBefore(now)){
            throw new DateExpiredException(" | (Date) = " + now );
        }
        return (int) Math.abs(DAYS.between(now, expirationDate));
    }

    @Override
    public List<Note> findAllOrderByPriceAsc() {
        List<Note> notes = noteRepository.findAllByOrderByPriceAsc();
        return refreshNotesDaysRemaining(notes);
    }

    @Override
    public List<Note> findAllOrderByPriceDesc() {
        List<Note> notes = noteRepository.findAllByOrderByPriceDesc();
        return refreshNotesDaysRemaining(notes);
    }

    @Override
    public List<Note> findByType(String type) {
        List<Note> notes = noteRepository.findByTypeIgnoringCase(type);
        return refreshNotesDaysRemaining(notes);
    }

    @Override
    public int compareTo(String order) {
        if (order.toString().equalsIgnoreCase("ASC")) {
            return 1;
        } else if (order.toString().equalsIgnoreCase("DESC")) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean existsById(Long id) {
        return noteRepository.existsById(id);
    }

    
 
    

}
