package com.voronin.auth.domain;

/**
 * Class message for email.
 *
 * @author Alexey Voronin.
 * @since 04.03.2019.
 */
public class Message {

    /**
     * Address for send message.
     */
    private String address;

    /**
     * Message subject.
     */
    private String subject;

    /**
     * Message body.
     */
    private String body;

    /**
     * Empty constructor.
     */
    public Message() {
    }

    /**
     * Constructor.
     *
     * @param address Address for send message.
     * @param subject Message subject.
     * @param body    Message body.
     */
    public Message(final String address, final String subject, final String body) {
        this.address = address;
        this.subject = subject;
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("Message {address - %s, subject - %s, body - %s}", getAddress(), getSubject(), getBody());
    }
}
