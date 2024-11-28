package com.jw.mail.smtp.entity;

import com.jw.mail.core.entity.MailAttachment;
import jakarta.activation.DataHandler;
import jakarta.mail.util.ByteArrayDataSource;

import java.io.ByteArrayInputStream;

public class SmtpAttachment
        extends MailAttachment<DataHandler> {

    public SmtpAttachment(String fileName, byte[] data, String contentType) {
        super(fileName, data, contentType);
    }

    public DataHandler toMailProviderAttachmentFormat() {
        try {
            byte[] data = getData();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            String contentType = getContentType();
            ByteArrayDataSource dataSource = new ByteArrayDataSource(inputStream, contentType);
            return new DataHandler(dataSource);
        } catch (Exception e) {
            //TODO[JW]: Error for such case
            throw new RuntimeException("Failed to create DataSource for attachment: " + getFileName(), e);
        }
    }

}
