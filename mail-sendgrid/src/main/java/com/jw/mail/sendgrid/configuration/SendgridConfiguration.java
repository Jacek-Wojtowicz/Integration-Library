package com.jw.mail.sendgrid.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sendgrid")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendgridConfiguration {

    private String apiKey;

    private String sender;

}
