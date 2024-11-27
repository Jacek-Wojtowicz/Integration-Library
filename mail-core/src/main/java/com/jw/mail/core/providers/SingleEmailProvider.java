package com.jw.mail.core.providers;

public interface SingleEmailProvider {
    void sendEmail(String to, String subject, String body);
}