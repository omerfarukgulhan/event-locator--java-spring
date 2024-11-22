package com.ofg.event.service.abstracts;

public interface SmsSenderService {
    void sendSms(String toPhoneNumber, String messageBody);
}
