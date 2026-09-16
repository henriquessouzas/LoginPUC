package com.example.LoginPUC.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.LoginPUC.exception.TokenInvalidoException;
import com.example.LoginPUC.service.SendEmailService;

@Controller
public class LoginController {

    private final SendEmailService sendEmailService;

    // token -> [email, expiracao]
    private final Map<String, Object[]> tokenStore = new ConcurrentHashMap<>();

    public LoginController(SendEmailService sendEmailService) {
        this.sendEmailService = sendEmailService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("cpf") String cpf,
            @RequestParam("rg") String rg,
            @RequestParam("endereco") String endereco,
            @RequestParam("instituicao") String instituicao,
            @RequestParam("senha") String senha) {

        return "redirect:/login";
    }

    @GetMapping("/esqueciSenha")
    public String esqueciSenha() {
        return "esqueciSenha";
    }

    @PostMapping("/esqueciSenha")
    public String handleEsqueciSenha(@RequestParam("email") String email, Model model) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, new Object[]{email, LocalDateTime.now().plusMinutes(30)});
        sendEmailService.sendRecoveryEmail(email, token);
        model.addAttribute("sucesso", "E-mail enviado! Verifique sua caixa de entrada.");
        return "esqueciSenha";
    }

    @GetMapping("/resetSenha")
    public String resetSenha(@RequestParam("token") String token, Model model) {
        Object[] data = tokenStore.get(token);
        if (data == null || LocalDateTime.now().isAfter((LocalDateTime) data[1]))
            throw new TokenInvalidoException("Link inválido ou expirado.");
        model.addAttribute("token", token);
        return "resetSenha";
    }

    @PostMapping("/resetSenha")
    public String handleResetSenha(
            @RequestParam("token") String token,
            @RequestParam("senha") String senha) {

        Object[] data = tokenStore.get(token);
        if (data == null || LocalDateTime.now().isAfter((LocalDateTime) data[1]))
            throw new TokenInvalidoException("Link inválido ou expirado.");

        // Aqui: atualizar a senha do usuário com o e-mail em data[0]
        tokenStore.remove(token);
        return "redirect:/login";
    }
}
