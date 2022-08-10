package com.api.diary.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.api.diary.dto.note.NoteDto;
import com.api.diary.models.Note;
import com.api.diary.service.NoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Note")
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {GET, POST, PUT, DELETE})
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/all")
    @Operation(description = "Get all notes")
    public ResponseEntity<List<Note>> findAll(){
        return ResponseEntity.ok(noteService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(description = "Get a note by his ID")
    public ResponseEntity<Note> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(noteService.findById(id));
    }

    @PostMapping("/new")
    @Operation(description = "Create and save a new note")
    public ResponseEntity<Note> create(@RequestBody NoteDto dto){
        return new ResponseEntity<>(noteService.create(dto), CREATED);
    }

    @PutMapping("/update")
    @Operation(description = "Update and save a note by his ID")
    public ResponseEntity<Note> update(@RequestParam("id") Long id,
                                       @RequestParam(value = "newDescription", required = false) String description,
                                       @RequestParam(value = "newPrice", required = false) Double price){

        NoteDto dto = new NoteDto(description, price, null, null);        
        return ResponseEntity.ok(noteService.update(dto, id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Delete a note by his ID")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        if(noteService.delete(id)){
            return ResponseEntity.ok("Deleted successful");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(params = "order")
    @Operation(description = "Get all notes order by 'ASC' or 'DESC' ignoring case, if param is != or null, return all")
    public ResponseEntity<List<Note>> findAllOrderByPrice(@RequestParam(value = "order", required = false) String order){
        if(noteService.compareTo(order) == 1){
            return ResponseEntity.ok(noteService.findAllOrderByPriceAsc());
        }
        else if(noteService.compareTo(order) == 0){
            return ResponseEntity.ok(noteService.findAllOrderByPriceDesc());
        }
        return ResponseEntity.ok(noteService.findAll());
    }

    
}
