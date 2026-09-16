package com.example.LoginPUC.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SendEmailException.class)
    public String handleSendEmail(SendEmailException e, Model model) {
        model.addAttribute("erro", e.getMessage());
        return "esqueciSenha";
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public String handleTokenInvalido(TokenInvalidoException e, Model model) {
        model.addAttribute("erro", e.getMessage());
        return "esqueciSenha";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception e, Model model) {
        model.addAttribute("erro", "Ocorreu um erro inesperado. Tente novamente.");
        return "error";
    }
}
