package com.example.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
public class TwilioController {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @PostMapping("/enviar-sms")
    public void enviarSMS(@RequestBody MensagemSMS mensagem) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                new PhoneNumber(mensagem.getDestinatario()),
                new PhoneNumber("+12055493757"), // Seu número do Twilio
                mensagem.getTexto()
        ).create();
    }
}

