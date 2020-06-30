package com.dsleandro.emailsender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

  @Value("${company.email}")
  private String EMAIL_ADDRESS;

  @Value("${company.name}")
  private String COMPANY;

  @Autowired
  private JavaMailSender emailSender;

  public void sendSimpleMessage(String to, String subject, String text) {
    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, false);

      helper.setFrom(EMAIL_ADDRESS, COMPANY);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text);

      emailSender.send(message);
    } catch (MessagingException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
    try {
      MimeMessage message = emailSender.createMimeMessage();
      // pass 'true' to the constructor to create a multipart message
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(EMAIL_ADDRESS, COMPANY);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text);

      File file = new File(pathToAttachment);
      helper.addAttachment("Invoice", file);

      emailSender.send(message);
    } catch (MessagingException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

}