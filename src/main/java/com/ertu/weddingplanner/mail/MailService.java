package com.ertu.weddingplanner.mail;

import com.ertu.weddingplanner.Locale;
import com.ertu.weddingplanner.guest.Guest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;


    @Autowired
    public MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendHtmlMail(Guest guest) throws MessagingException, UnsupportedEncodingException {
        Context context = new Context();
        context.setVariable("name", guest.getName());
        String process = templateEngine.process(chooseTemplate(guest.getLocale(), guest.isAttending()), context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(getSubject(guest.getLocale()));
        helper.setFrom(new InternetAddress("elisabeth.ertugrul@gmail.com", "Elisabeth & Ertugrul Kurnaz"));
        helper.setText(process, true);
        helper.setTo(guest.getEmail());
        mailSender.send(mimeMessage);
    }

    private String chooseTemplate(Locale locale, boolean isAttenting) {
        return isAttenting ? getAcceptTemplate(locale) : getRejectTemplate(locale);
    }

    private String getAcceptTemplate(Locale locale) {
        Map<Locale, String> templates = Map.of(
                Locale.AR, "acceptArabic",
                Locale.DE, "acceptGerman",
                Locale.GB, "acceptEnglish",
                Locale.TR, "acceptTurkish",
                Locale.JP, "acceptJapanese",
                Locale.RU, "acceptRussian"
        );
        return templates.getOrDefault(locale, "");
    }


    private String getRejectTemplate(Locale locale) {
        Map<Locale, String> templates = Map.of(
                Locale.AR, "rejectArabic",
                Locale.DE, "rejectGerman",
                Locale.GB, "rejectEnglish",
                Locale.TR, "rejectTurkish",
                Locale.JP, "rejectJapanese",
                Locale.RU, "rejectRussian"
        );
        return templates.getOrDefault(locale, "");
    }

    private String getSubject(Locale locale) {
        Map<Locale, String> subjects = Map.of(
                Locale.AR, "تسجيلك لحفل زفافنا",
                Locale.DE, "Ihre Anmeldung zu unserer Hochzeit",
                Locale.GB, "Your registration for our wedding",
                Locale.TR, "Düğünümüz için kaydınız",
                Locale.JP, "私たちの結婚式のためのあなたの登録",
                Locale.RU, "Ваша регистрация на нашу свадьбу"
        );
        return subjects.getOrDefault(locale, "");
    }

}
