package com.digyai.notificationservice.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send HTML notification email.
     * Called by NotificationService after saving to Redis.
     */
    public void sendNotificationEmail(String toEmail, String userId, String eventType, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(getSubject(eventType));
            helper.setText(buildHtmlEmail(userId, eventType, message), true);

            mailSender.send(mimeMessage);
            logger.info("[EMAIL-SERVICE] Email sent to={} | userId={} | eventType={}", toEmail, userId, eventType);

        } catch (Exception e) {
            // Email failure should NEVER break the main notification flow
            logger.error("[EMAIL-SERVICE] Failed to send email to={} | error={}", toEmail, e.getMessage());
        }
    }

    private String getSubject(String eventType) {
        return switch (eventType) {
            case "ORDER_PLACED"    -> "✅ Order Confirmed — ShopFlow";
            case "PAYMENT_DONE"   -> "💳 Payment Successful — ShopFlow";
            case "SHIPMENT_UPDATE" -> "🚚 Shipment Update — ShopFlow";
            default               -> "🔔 New Notification — ShopFlow";
        };
    }

    private String buildHtmlEmail(String userId, String eventType, String message) {
        String icon = switch (eventType) {
            case "ORDER_PLACED"    -> "📦";
            case "PAYMENT_DONE"   -> "💳";
            case "SHIPMENT_UPDATE" -> "🚚";
            default               -> "🔔";
        };

        String badgeColor = switch (eventType) {
            case "ORDER_PLACED"    -> "#f97316";
            case "PAYMENT_DONE"   -> "#22d3ee";
            case "SHIPMENT_UPDATE" -> "#a78bfa";
            default               -> "#6b7280";
        };

        return """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8"/>
              <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
            </head>
            <body style="margin:0;padding:0;background:#f3f4f6;font-family:Arial,sans-serif;">
              <table width="100%%" cellpadding="0" cellspacing="0" style="padding:40px 0;">
                <tr><td align="center">
                  <table width="540" cellpadding="0" cellspacing="0" style="background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 24px rgba(0,0,0,0.08);">

                    <!-- Header -->
                    <tr>
                      <td style="background:#0a0a0f;padding:28px 32px;text-align:center;">
                        <div style="font-size:26px;font-weight:800;color:#ffffff;letter-spacing:-0.5px;">ShopFlow</div>
                        <div style="font-size:12px;color:#6b6b80;letter-spacing:2px;text-transform:uppercase;margin-top:4px;">Notification Service</div>
                      </td>
                    </tr>

                    <!-- Icon + event badge -->
                    <tr>
                      <td style="padding:32px 32px 16px;text-align:center;">
                        <div style="font-size:48px;margin-bottom:16px;">%s</div>
                        <span style="background:%s;color:#ffffff;font-size:12px;font-weight:700;padding:4px 14px;border-radius:999px;letter-spacing:1px;text-transform:uppercase;">%s</span>
                      </td>
                    </tr>

                    <!-- Message -->
                    <tr>
                      <td style="padding:16px 32px 32px;">
                        <div style="background:#f8fafc;border-left:4px solid %s;border-radius:0 8px 8px 0;padding:16px 20px;font-size:16px;color:#1a1a1a;line-height:1.6;">
                          %s
                        </div>
                        <p style="font-size:13px;color:#6b7280;margin-top:16px;">
                          This notification was generated for <strong>%s</strong> and delivered in real time via Apache Kafka &amp; WebSocket.
                        </p>
                      </td>
                    </tr>

                    <!-- Footer -->
                    <tr>
                      <td style="background:#f8fafc;padding:20px 32px;text-align:center;border-top:1px solid #e5e7eb;">
                        <p style="font-size:12px;color:#9ca3af;margin:0;">
                          ShopFlow · Kafka-Based Real-Time Notification System<br/>
                          BTech CSE Final Year Project · Central University of Rajasthan
                        </p>
                      </td>
                    </tr>

                  </table>
                </td></tr>
              </table>
            </body>
            </html>
            """.formatted(icon, badgeColor, eventType.replace("_", " "), badgeColor, message, userId);
    }
}
