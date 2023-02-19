package com.ertu.weddingplanner.mail;

import com.ertu.weddingplanner.guest.Guest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;


    @Autowired
    public MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendHtmlMail(Guest guest, String subject) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("name", guest.getName());
        String process = templateEngine.process("email", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setText(process, true);
        helper.setTo(guest.getEmail());
        mailSender.send(mimeMessage);
    }

}
