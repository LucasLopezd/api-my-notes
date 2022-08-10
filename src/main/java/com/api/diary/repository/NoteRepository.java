package com.api.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.diary.models.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{
    
    boolean existsById(Long id);
    //Expired = TRUE
    List<Note> findByExpiredTrue();

    List<Note> findByExpiredTrueAndPaidTrue();

    List<Note> findByExpiredTrueAndPaidFalse();

    //Expired = FALSE
    List<Note> findByExpiredFalse();

    List<Note> findByExpiredFalseAndPaidTrue();

    List<Note> findByExpiredFalseAndPaidFalse();

    //Type
    List<Note> findByTypeIgnoringCase(String type);

    //Order - Price
    List<Note> findAllByOrderByPriceAsc();

    List<Note> findAllByOrderByPriceDesc();
}
