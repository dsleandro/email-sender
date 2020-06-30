package com.dsleandro.emailsender.service;

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);
                           
    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment);
}