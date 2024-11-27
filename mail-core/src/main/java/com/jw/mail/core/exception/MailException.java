package com.jw.mail.core.exception;

import com.jw.core.exception.LibraryException;
import com.jw.core.type.ModuleType;
import com.jw.mail.core.type.ProviderType;
import lombok.Getter;

public class MailException
        extends LibraryException {

    @Getter
    private final ProviderType type;

    public MailException(ProviderType type, String message, Throwable cause) {
        super(ModuleType.EMAIL, message, cause);
        this.type = type;
    }

}
