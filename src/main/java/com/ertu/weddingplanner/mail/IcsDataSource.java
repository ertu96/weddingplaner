package com.ertu.weddingplanner.mail;

import jakarta.activation.DataSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class IcsDataSource implements DataSource {
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