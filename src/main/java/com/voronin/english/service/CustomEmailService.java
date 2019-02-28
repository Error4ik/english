package com.voronin.english.service;

import com.google.common.collect.Lists;
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
     * @param addressee addressee.
     * @param subject   subject.
     * @param body      body.
     */
    public void send(final String addressee, final String subject, final String body) throws UnsupportedEncodingException {
        logger.debug(String.format("Arguments - %s, %s, %s", addressee, subject, body));
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress(emailFrom, "Support english.ru"))
                .to(Lists.newArrayList(new InternetAddress(addressee, addressee)))
                .subject(subject)
                .body(body)
                .encoding("UTF-8").build();
        emailService.send(email);
    }
}
