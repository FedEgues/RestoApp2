
package com.RestoApp2.web.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServicio {
    @Autowired
    private JavaMailSender javaMailSender;
    
    public void enviarMail(){
    
    }
}
