package com.api.diary.util.service;

import java.time.LocalDate;
import java.util.List;

import com.api.diary.dto.note.NoteDto;
import com.api.diary.models.Note;

public interface NoteServiceUtils extends BasicCrud<NoteDto, Note>{
    
    int getDaysRemaining(LocalDate expirationDate);

    boolean existsById(Long id);

    List<Note> findAllOrderByPriceAsc();

    List<Note> findAllOrderByPriceDesc();

    List<Note> findByType(String type);
}
