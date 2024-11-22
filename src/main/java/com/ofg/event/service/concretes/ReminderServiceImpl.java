package com.ofg.event.service.concretes;

import com.ofg.event.model.entity.Event;
import com.ofg.event.model.entity.FcmToken;
import com.ofg.event.model.entity.Registration;
import com.ofg.event.service.abstracts.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {
    private final SmsSenderService smsSenderService;
    private final EmailService emailService;
    private final RegistrationService registrationService;
    private final EventService eventService;
    private final NotificationService notificationService;
    private final FcmTokenService fcmTokenService;

    @Autowired
    public ReminderServiceImpl(SmsSenderService smsSenderService,
                               EmailService emailService,
                               RegistrationService registrationService,
                               EventService eventService,
                               NotificationService notificationService, FcmTokenService fcmTokenService) {
        this.smsSenderService = smsSenderService;
        this.emailService = emailService;
        this.registrationService = registrationService;
        this.eventService = eventService;
        this.notificationService = notificationService;
        this.fcmTokenService = fcmTokenService;
    }

    @Scheduled(cron = "0 0,30 * * * *")
    @Transactional
    @Override
    public void sendReminders() {
        int minutes = 60;
        List<Event> events = eventService.getUpcomingEventsWithinMinutes(minutes);
        List<Registration> registrations = new java.util.ArrayList<>(List.of());
        for (Event event : events) {
            List<Registration> eventRegistrations = registrationService.getRegistrationsForEvent(event.getId());
            registrations.addAll(eventRegistrations);
        }

        for (Registration registration : registrations) {
            String message = String.format("Your appointment for %s is in %d minutes.", registration.getEvent().getName(), minutes);
            smsSenderService.sendSms(registration.getUser().getPhoneNumber(), message);

            FcmToken fcmToken = fcmTokenService.getFcmTokenByUserId(registration.getUser().getId());
            String subject = "Event Reminder";
            notificationService.sendNotification(fcmToken.getFcmToken(), subject, message);
            emailService.sendEmail(registration.getUser().getEmail(), message, subject);
        }
    }
}


