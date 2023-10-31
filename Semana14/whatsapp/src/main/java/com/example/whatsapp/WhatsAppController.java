package com.example.whatsapp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhatsAppController {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.whatsapp.phoneNumber}")
    private String fromPhoneNumber;

    @PostMapping("/enviar-mensagem-whatsapp")
    public void enviarMensagemWhatsApp(@RequestBody MensagemWhatsApp mensagem) {
        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber("whatsapp:" + mensagem.getDestinatario()), // Prefixo "whatsapp:" é obrigatório
                new PhoneNumber("whatsapp:" + fromPhoneNumber), // Seu número do WhatsApp Business
                mensagem.getTexto()
        ).create();
    }
}

