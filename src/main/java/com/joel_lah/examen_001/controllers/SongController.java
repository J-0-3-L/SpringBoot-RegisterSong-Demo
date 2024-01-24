package com.joel_lah.examen_001.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joel_lah.examen_001.models.Song;
import com.joel_lah.examen_001.models.User;
import com.joel_lah.examen_001.servicies.SongServicio;
import com.joel_lah.examen_001.servicies.UserServicio;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongServicio songServicio;
    @Autowired
    private UserServicio userServicio;

    // crear musica
    @GetMapping("/new")
    public String viewAddSong(Model model) {
        model.addAttribute("song", new Song());
        return "add_song";
    }
    @PostMapping("/new")
    public String addSong(@Valid @ModelAttribute("song") Song song, BindingResult result,HttpSession session) {
        if (result.hasErrors()) {
            return "add_song";
        }
        User creator = userServicio.findUserById((Long) session.getAttribute("user_id"));
        song.setCreator(creator);
        songServicio.save(song);
        return "redirect:/home";
    }

    // ver detalles de musica mediante id
    @GetMapping("/{id}")
    public String viewIdSong(@PathVariable Long id, Model model) {
        Song song = songServicio.findById(id);
        User user = song.getCreator();
        model.addAttribute("song", song);
        model.addAttribute("user", user);
        return "detail";
    }

    // Vista para editar una canción existente
    @GetMapping("/{id}/edit")
    public String viewEditSong(@PathVariable Long id, Model model) {
        Song song = songServicio.findById(id);
        model.addAttribute("song", song);
        return "edit_song";
    }
    @PostMapping("/{id}/edit")
    public String editSong(@PathVariable Long id, @Valid @ModelAttribute("song") Song updatedSong, BindingResult result,
            HttpSession session) {
        if (result.hasErrors()) {
            return "edit_song";
        }
        Song cancionActualizada = songServicio.findById(id);
        cancionActualizada.setTitle(updatedSong.getTitle());
        cancionActualizada.setGenere(updatedSong.getGenere());
        cancionActualizada.setLyrics(updatedSong.getLyrics());
        User currentUser = userServicio.findUserById((Long) session.getAttribute("user_id"));
        cancionActualizada.getUsers().add(currentUser);
        songServicio.updateEditors(cancionActualizada);
        return "redirect:/home";
    }

    // Procesar la solicitud de eliminar una canción
    @GetMapping("/{id}/delete")
    public String deleteSong(@PathVariable Long id) {
        songServicio.delete(id);
        return "redirect:/home";
    }
}
