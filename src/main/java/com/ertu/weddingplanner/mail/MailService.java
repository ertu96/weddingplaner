package com.ertu.weddingplanner.mail;

import com.ertu.weddingplanner.Locale;
import com.ertu.weddingplanner.guest.Guest;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
        // Your existing code for processing the HTML content
        Context context = new Context();
        context.setVariable("name", guest.getName());
        String process = templateEngine.process(chooseTemplate(guest.getLocale(), guest.isAttending()), context);

        // Create a MimeMessage
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Set subject, sender, recipient, and HTML content
        helper.setSubject(getSubject(guest.getLocale()));
        helper.setFrom(new InternetAddress("elesa6899@gmail.com", "Elisabeth & Ertugrul Kurnaz"));
        helper.setTo(guest.getEmail());
        helper.setText(process, true);

        // Create a MimeMultipart to hold both HTML content and .ics attachment
        MimeMultipart multipart = new MimeMultipart();

        // Create MimeBodyPart for HTML content
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(process, "text/html; charset=utf-8");
        multipart.addBodyPart(htmlPart);

        // Create MimeBodyPart for .ics file attachment
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setDataHandler(new DataHandler(new MyDataSource())); // Provide your own DataSource implementation
        attachmentPart.setFileName("event.ics"); // Set the filename of the attachment
        multipart.addBodyPart(attachmentPart);

        // Set the multipart as the content of the message
        mimeMessage.setContent(multipart);

        // Send the email
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

// Custom DataSource implementation for providing .ics file content
class MyDataSource implements DataSource {
    @Override
    public InputStream getInputStream() {
        // Provide the content of your .ics file as an InputStream
        String icsContent = """
                BEGIN:VCALENDAR
                VERSION:2.0
                PRODID:-//hacksw/handcal//NONSGML v1.0//EN
                BEGIN:VEVENT
                SUMMARY: Hochzeit Meliha & Ali
                DTSTART;VALUE=DATE:20240310
                DTEND;VALUE=DATE:20240311
                LOCATION: Platin Eventlocation - Bremerhavener Str. 25, 50735 Köln
                END:VEVENT
                END:VCALENDAR""";
        return new ByteArrayInputStream(icsContent.getBytes());
    }

    @Override
    public OutputStream getOutputStream() {
        return null; // Not needed for read-only access
    }

    @Override
    public String getContentType() {
        return "text/calendar; charset=utf-8"; // Set the content type of the .ics file
    }

    @Override
    public String getName() {
        return "hochzeit-einladung.ics"; // Set the filename of the .ics file
    }
}