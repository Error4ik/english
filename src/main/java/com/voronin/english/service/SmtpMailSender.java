package com.voronin.english.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * SmtpMailSender - send email.
 *
 * @author Alexey Voronin.
 * @since 12.02.2019.
 */
@Service
public class SmtpMailSender {

    /**
     * JavaMailSender.
     */
    private final JavaMailSender javaMailSender;

    /**
     * Constructor.
     *
     * @param javaMailSender JavaMailSender.
     */
    @Autowired
    public SmtpMailSender(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Send email.
     *
     * @param addressee send to.
     * @param subject   subject.
     * @param body      message.
     * @throws MessagingException exception.
     */
    public void send(final String addressee, final String subject, final String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setTo(addressee);
        helper.setText(body, true);

        javaMailSender.send(message);
    }
}
