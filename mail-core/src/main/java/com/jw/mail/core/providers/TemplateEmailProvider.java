package com.jw.mail.core.providers;

import com.jw.core.variable.VariablePairs;

import java.util.Map;

public interface TemplateEmailProvider {
    void sendTemplateEmail(String to, String subject, String templateName, VariablePairs variables);
}
