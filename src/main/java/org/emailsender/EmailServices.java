package org.emailsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
public class EmailServices {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    TemplateEngine templateEngine;

    @GetMapping("/send")
    public void sendEmail(){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("beril@gmail.com");
        message.setTo("emre@gmail.com");
        message.setSubject("Test Mail");
        message.setText("Hello!! I am bad girl!!!");
        emailSender.send(message);
    }

    @GetMapping("/sendwithhtml")
    public void sendEmailWithHtml(){
        Context context = new Context();
        context.setVariable("message", "Heyy are You okayy!!");

        String message = templateEngine.process("mailtemplate", context);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("sample@gmail.com");
            messageHelper.setTo("emre@gmail.com");
            messageHelper.setSubject("Html MAIL");
            messageHelper.setText(message);
        };

        try {
            emailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
    }
}
