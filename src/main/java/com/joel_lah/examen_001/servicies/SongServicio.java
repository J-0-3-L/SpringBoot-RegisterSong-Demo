package com.joel_lah.examen_001.servicies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joel_lah.examen_001.models.Song;
import com.joel_lah.examen_001.repositories.SongRepository;

@Service
public class SongServicio extends BaseServicio<Song> {

    @Autowired
    private SongRepository songRepo;

    public Song findById(Long id) {
        return songRepo.findById(id).orElse(null);
    }

    // Agrega este método para actualizar la lista de usuarios editores
    public void updateEditors(Song song) {
        songRepo.save(song);
    }

    public List<Song> findAll() {
        return songRepo.findAll();
    }
    public int countEditors(Song song) {
        return song.getUsers().size();
    }

    public List<Integer> calculateEditorCounts(List<Song> songs, Long userId) {
        List<Integer> editorCounts = new ArrayList<>();
        for (Song song : songs) {
            int editorCount = countEditors(song);
            editorCounts.add(editorCount);
        }
        return editorCounts;
    }
}

