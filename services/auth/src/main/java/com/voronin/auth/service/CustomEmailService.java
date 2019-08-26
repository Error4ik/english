package com.voronin.auth.service;

import com.google.common.collect.Lists;
import com.voronin.auth.domain.Message;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

/**
 * CustomEmailService - send email.
 *
 * @author Alexey Voronin.
 * @since 12.02.2019.
 */
@Service
public class CustomEmailService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(CustomEmailService.class);

    /**
     * Email from.
     */
    @Value("${spring.mail.username}")
    private String emailFrom;

    /**
     * EmailService.
     */
    private final EmailService emailService;

    /**
     * Constructor.
     *
     * @param emailService EmailService.
     */
    @Autowired
    public CustomEmailService(final EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Send email.
     *
     * @param message Message for email.
     */
    public void send(final Message message) throws UnsupportedEncodingException {
        logger.debug(String.format("Arguments - %s", message));
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress(emailFrom, "Support english.ru"))
                .to(Lists.newArrayList(new InternetAddress(message.getAddress(), message.getAddress())))
                .subject(message.getSubject())
                .body(message.getBody())
                .encoding("UTF-8").build();
        emailService.send(email);
    }
}
