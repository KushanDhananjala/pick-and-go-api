package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.Mail;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(Mail mail, String template) throws MessagingException;

}
