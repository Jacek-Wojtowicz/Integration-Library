package com.jw.mail.core.providers;

public interface HtmlEmailProvider {
    void sendHtmlEmail(String to, String subject, String htmlContent);
}

