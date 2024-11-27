package com.jw.mail.smtp.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "smtp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmtpConfiguration {

    private String host;

    private int port;

    private String username;

    private String password;

    private String from;

    private boolean auth;

    private boolean tls;

}
