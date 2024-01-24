package com.joel_lah.examen_001.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joel_lah.examen_001.models.Song;
import com.joel_lah.examen_001.models.User;
import com.joel_lah.examen_001.servicies.SongServicio;
import com.joel_lah.examen_001.servicies.UserServicio;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserServicio userService;

    @Autowired
    private SongServicio songServicio;

    public UserController(UserServicio userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        List<Song> songs = songServicio.findAll();
        User current = userService.findUserById((Long) session.getAttribute("user_id"));
        List<Integer> editorCounts = songServicio.calculateEditorCounts(songs, current.getId());

        model.addAttribute("usuario", current);
        model.addAttribute("songs", songs);
        model.addAttribute("editorCounts", editorCounts);
        return "index";
    }

    // para ingresar usuario
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {
        if (!userService.authenticateUser(email, password)) {
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
        User authUser = userService.findByEmail(email);
        session.setAttribute("user_id", authUser.getId());
        return "redirect:/home";
    }

    // registro de usuario
    @GetMapping("/register")
    public String Register(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping("/register")
    public String createUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            HttpSession session) {
        if (result.hasErrors()) {
            return "register";
        }
        User newUser = userService.registerUser(user);
        session.setAttribute("user_id", newUser.getId());
        return "redirect:/home";
    }

    // para cerrar usuario
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user_id");
        return "redirect:/";
    }
}
