package com.voronin.auth.service;

import com.voronin.auth.domain.Message;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.service.EmailService;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * CustomEmailService test class.
 *
 * @author Alexey Voronin.
 * @since 15.04.2019.
 */
public class CustomEmailServiceTest {

    /**
     * Mock EmailService.
     */
    private EmailService emailService = mock(EmailService.class);

    /**
     * The class object under test.
     */
    private CustomEmailService customEmailService = new CustomEmailService(emailService);

    /**
     * When Should.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenShould() throws Exception {
        customEmailService.send(new Message("address", "subject", "body"));
        verify(emailService, times(1)).send(any(Email.class));
    }
}
