package com.jw.mail.sendgrid.entity;

import com.jw.mail.core.entity.MailAttachment;

import java.util.Base64;

public class SendgridAttachment
        extends MailAttachment<String> {

    public SendgridAttachment(String fileName, byte[] data, String contentType) {
        super(fileName, data, contentType);
    }

    @Override
    public String toMailProviderAttachmentFormat() {
        byte[] data = getData();
        return Base64.getEncoder().encodeToString(data);
    }

}
