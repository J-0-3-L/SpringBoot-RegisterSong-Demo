package com.joel_lah.examen_001.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "songs")
@NoArgsConstructor
@Getter
@Setter
public class Song extends ModelBase{
    
    @NotNull(message = "El título es obligatorio")
    @NotBlank(message = "El título no puede estar en blanco")
    private String title;

    @NotNull(message = "El género es obligatorio")
    @NotBlank(message = "El género no puede estar en blanco")
    private String genere;

    @NotNull(message = "La letra es obligatoria")
    @NotBlank(message = "La letra no puede estar en blanco")
    private String lyrics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "users_songs", 
        joinColumns = @JoinColumn(name = "song_id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

}
