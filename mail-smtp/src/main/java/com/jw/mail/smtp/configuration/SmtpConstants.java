package com.jw.mail.smtp.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SmtpConstants {

    public static final String AUTH = "mail.smtp.auth";

    public static final boolean DEFAULT_ENABLE_TLS = true;

    public static final String HOST = "mail.smtp.host";

    public static final String PORT = "mail.smtp.port";


}
