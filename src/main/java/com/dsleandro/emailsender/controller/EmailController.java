package com.dsleandro.emailsender.controller;

import com.dsleandro.emailsender.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {

    @Autowired
    public EmailService emailService;

    @Value("${attachment.invoice}")
    private String attachmentPath;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam("to") String to,
        @RequestParam("subject") String subject, @RequestParam("text") String text,
        @RequestParam(value = "attachment", required = false) boolean attachment,
        Model model){

        model.addAttribute("Message", "Email sent successfully");

        //if attachment is checked, sen with attachmnet.. otherwise send simple email

        return attachment? createMailWithAttachment(to, subject, text) 
                            : createMail(to, subject, text);
    }

    
    private String createMail(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("text") String text) {

        emailService.sendSimpleMessage(to, subject, text);

        return "index";
    }

    
    private String createMailWithAttachment(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("text") String text) {

        emailService.sendMessageWithAttachment(to, subject, text, attachmentPath);

        return "index";
    }
}