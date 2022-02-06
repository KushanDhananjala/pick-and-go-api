package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.Mail;
import edu.esoft.sdp.cw.pickandgoapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Override
    public void sendEmail(Mail mail, String template) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(mail.getProps());
        String htmlBody = templateEngine.process(template, thymeleafContext);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("kushan.microsoft16@gmail.com");
        helper.setTo(mail.getMailTo());
        helper.setSubject(mail.getSubject());
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }

}
