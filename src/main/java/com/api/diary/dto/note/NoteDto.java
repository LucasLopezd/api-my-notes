package com.api.diary.dto.note;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
        
    private String noteDescription;
    private Double notePrice;
    private String noteType;
    private LocalDate noteExpirationDate;
}

    