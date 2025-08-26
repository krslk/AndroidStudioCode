package com.example.dailynews.Api;

import android.os.AsyncTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class UserApi extends AsyncTask<Void, Void, Boolean> {
    private static final String SMTP_HOST = "smtp.163.com";
    private static final String SMTP_PORT = "465";
    private static final String USERNAME = "lj531967491@163.com";
    private static final String PASSWORD = "QAUa35mMYRb8YLzy";

    public static boolean sendEmailCode(String email, String code) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", SMTP_HOST);
            properties.put("mail.smtp.port", SMTP_PORT);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.ssl.enable", "true");

            // 使用 GMail 的 SMTP 服务器进行身份验证
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(code);
            message.setText("验证码\" + code + \"，拒收请回复R\"");

            // 发送邮件
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return null;
    }
}
