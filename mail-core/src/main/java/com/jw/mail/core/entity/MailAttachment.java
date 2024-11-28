package com.jw.mail.core.entity;

import com.jw.core.entity.Attachment;

public abstract class MailAttachment<T>
        extends Attachment {

    public MailAttachment(String fileName, byte[] data, String contentType) {
        super(fileName, data, contentType);
    }

    public abstract T toMailProviderAttachmentFormat();

}
