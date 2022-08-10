package com.api.diary.util;

import java.time.LocalDate;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;

import com.api.diary.models.Note;

public class Utils {

    public static boolean checkParameters(Object object) {
        if (object instanceof Long) {
            return ((long) object > 0) ? true : false;
        }
        if (object instanceof Double) {
            return ((double) object > 0) ? true : false;
        }
        return (object != null) ? true : false;
    }

    public static Note refreshNoteDaysRemaining(Note note) {
        LocalDate now = LocalDate.now();

        if (note.getExpirationDate().isBefore(now)) {
            note.setExpired(true);
            note.setDaysRemaining(0);
            return note;
        }
        note.setDaysRemaining((int) Math.abs(DAYS.between(now, note.getExpirationDate())));
        return note;
    }

    public static List<Note> refreshNotesDaysRemaining(List<Note> notes) {
        LocalDate now = LocalDate.now();

        for (Note note : notes) {
            if (note.getExpirationDate().isBefore(now)) {
                note.setExpired(true);
                note.setDaysRemaining(0);
            } else {
                note.setDaysRemaining((int) Math.abs(DAYS.between(now, note.getExpirationDate())));
            }
        }
        return notes;
    }

}
