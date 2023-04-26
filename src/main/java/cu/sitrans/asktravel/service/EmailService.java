package cu.sitrans.asktravel.service;


import cu.sitrans.asktravel.models.Email;
import cu.sitrans.asktravel.models.Notification;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendSMail(Email email) throws MessagingException, UnsupportedEncodingException;
}
