package com.jw.mail.core.providers;

import com.jw.mail.core.entity.MailAttachment;

import java.util.List;

public interface AttachmentEmailProvider<T extends MailAttachment<?>> {

    void sendEmailWithAttachments(String to, String subject, String body, List<T> mailAttachments);

}
