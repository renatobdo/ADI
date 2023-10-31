package com.example.whatsapp;

public class MensagemWhatsApp {
    private String destinatario;
    private String texto;

    public MensagemWhatsApp() {
        // Construtor vazio
    }

    public MensagemWhatsApp(String destinatario, String texto) {
        this.destinatario = destinatario;
        this.texto = texto;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}

