package com.api.diary.models;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import static javax.persistence.FetchType.EAGER;

import org.hibernate.annotations.SQLDelete;

import com.api.diary.enums.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET user_enabled = FALSE WHERE user_id = ?")
@Getter
@Setter
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "user_email", length = 60, unique = true, nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @OneToMany(fetch = EAGER)
    @JoinTable(name = "users_notes",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
               inverseJoinColumns =  @JoinColumn(name = "note_id", referencedColumnName = "note_id"))
    private List<Note> notes;

    @Enumerated(STRING)
    private Role role;

    @Column(name = "user_enabled", nullable = false)
    private Boolean enabled;

    

}
