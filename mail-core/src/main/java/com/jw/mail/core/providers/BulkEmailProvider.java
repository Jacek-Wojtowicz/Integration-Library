package com.jw.mail.core.providers;

public interface BulkEmailProvider {

    void sendBulkEmail(String[] to, String subject, String body);

}
