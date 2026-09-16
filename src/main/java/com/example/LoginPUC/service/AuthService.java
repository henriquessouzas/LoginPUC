package com.example.LoginPUC.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

    // Substituir por consulta ao banco quando disponível
    private static final String USUARIO_FIXO = "admin";
    private static final String SENHA_FIXA = "admin123";

    public boolean autenticar(String username, String senha, HttpSession session) {
        if (USUARIO_FIXO.equals(username) && SENHA_FIXA.equals(senha)) {
            session.setAttribute("usuarioLogado", username);
            return true;
        }
        return false;
    }

    public boolean estaLogado(HttpSession session) {
        return session.getAttribute("usuarioLogado") != null;
    }

    public String getUsuario(HttpSession session) {
        return (String) session.getAttribute("usuarioLogado");
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
