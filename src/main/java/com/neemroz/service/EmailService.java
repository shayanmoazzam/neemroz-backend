package com.neemroz.service;

import com.neemroz.model.Order;
import com.neemroz.model.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromAddress;

    /**
     * Sends a beautiful HTML order confirmation email to the customer.
     * @Async ensures it runs in a background thread — order placement is NOT delayed.
     */
    @Async
    public void sendOrderConfirmation(Order order) {
        String toEmail = order.getUser().getEmail();
        if (toEmail == null || toEmail.isBlank()) {
            log.warn("No email found for user {}, skipping confirmation email", order.getUser().getId());
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(toEmail);
            helper.setSubject("✅ Order Confirmed! #" + order.getOrderNumber() + " — Ayezu Collection");
            helper.setText(buildEmailHtml(order), true); // true = HTML

            mailSender.send(message);
            log.info("Order confirmation email sent to {} for order {}", toEmail, order.getOrderNumber());

        } catch (MessagingException e) {
            log.error("Failed to send order confirmation email for order {}: {}", order.getOrderNumber(), e.getMessage());
        }
    }

    private String buildEmailHtml(Order order) {
        StringBuilder itemRows = new StringBuilder();
        for (OrderItem item : order.getItems()) {
            itemRows.append("""
                    <tr>
                      <td style="padding:10px 8px;border-bottom:1px solid #f0ece6;">
                        <strong style="color:#2d2a22;">%s</strong>
                        %s
                      </td>
                      <td style="padding:10px 8px;border-bottom:1px solid #f0ece6;text-align:center;color:#5a5750;">%d</td>
                      <td style="padding:10px 8px;border-bottom:1px solid #f0ece6;text-align:right;color:#2d2a22;font-weight:600;">₹%s</td>
                    </tr>
                    """.formatted(
                    item.getProduct().getName(),
                    buildVariantBadges(item),
                    item.getQuantity(),
                    item.getTotalPrice().toPlainString()
            ));
        }

        String paymentBadgeColor = order.getPaymentMethod() == Order.PaymentMethod.COD ? "#b45309" : "#01696f";
        String paymentLabel = order.getPaymentMethod() == Order.PaymentMethod.COD ? "Cash on Delivery" : "UPI";
        String orderDate = order.getCreatedAt() != null
                ? order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"))
                : "Just now";

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1"></head>
                <body style="margin:0;padding:0;background:#f7f6f2;font-family:'Segoe UI',Arial,sans-serif;">

                  <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f7f6f2;padding:32px 0;">
                    <tr><td align="center">

                      <table width="600" cellpadding="0" cellspacing="0" style="max-width:600px;width:100%%;background:#ffffff;border-radius:12px;overflow:hidden;box-shadow:0 4px 24px rgba(0,0,0,0.08);">

                        <!-- HEADER -->
                        <tr>
                          <td style="background:#01696f;padding:32px 40px;text-align:center;">
                            <h1 style="margin:0;color:#ffffff;font-size:26px;font-weight:700;letter-spacing:-0.5px;">Ayezu Collection</h1>
                            <p style="margin:6px 0 0;color:rgba(255,255,255,0.8);font-size:13px;">Premium Fashion for Every Occasion</p>
                          </td>
                        </tr>

                        <!-- SUCCESS BANNER -->
                        <tr>
                          <td style="background:#f0fdf4;padding:24px 40px;text-align:center;border-bottom:1px solid #d1fae5;">
                            <div style="font-size:40px;">🎉</div>
                            <h2 style="margin:8px 0 4px;color:#166534;font-size:20px;">Order Confirmed!</h2>
                            <p style="margin:0;color:#4ade80;font-size:14px;">Thank you for shopping with Ayezu Collection</p>
                          </td>
                        </tr>

                        <!-- ORDER META -->
                        <tr>
                          <td style="padding:24px 40px;border-bottom:1px solid #f0ece6;">
                            <table width="100%%" cellpadding="0" cellspacing="0">
                              <tr>
                                <td style="width:50%%;">
                                  <p style="margin:0 0 4px;font-size:11px;color:#9a9890;text-transform:uppercase;letter-spacing:0.6px;">Order ID</p>
                                  <p style="margin:0;font-size:15px;font-weight:700;color:#01696f;">#%s</p>
                                </td>
                                <td style="width:50%%;text-align:right;">
                                  <p style="margin:0 0 4px;font-size:11px;color:#9a9890;text-transform:uppercase;letter-spacing:0.6px;">Order Date</p>
                                  <p style="margin:0;font-size:13px;color:#5a5750;">%s</p>
                                </td>
                              </tr>
                              <tr>
                                <td colspan="2" style="padding-top:12px;">
                                  <span style="background:%s;color:#fff;font-size:11px;font-weight:600;padding:3px 10px;border-radius:999px;">%s</span>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>

                        <!-- ITEMS TABLE -->
                        <tr>
                          <td style="padding:24px 40px;">
                            <p style="margin:0 0 12px;font-size:14px;font-weight:700;color:#2d2a22;text-transform:uppercase;letter-spacing:0.5px;">Items Ordered</p>
                            <table width="100%%" cellpadding="0" cellspacing="0">
                              <thead>
                                <tr style="background:#f7f6f2;">
                                  <th style="padding:8px;text-align:left;font-size:11px;color:#9a9890;font-weight:600;text-transform:uppercase;">Product</th>
                                  <th style="padding:8px;text-align:center;font-size:11px;color:#9a9890;font-weight:600;text-transform:uppercase;">Qty</th>
                                  <th style="padding:8px;text-align:right;font-size:11px;color:#9a9890;font-weight:600;text-transform:uppercase;">Total</th>
                                </tr>
                              </thead>
                              <tbody>%s</tbody>
                            </table>
                          </td>
                        </tr>

                        <!-- PRICE SUMMARY -->
                        <tr>
                          <td style="padding:0 40px 24px;">
                            <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f7f6f2;border-radius:8px;padding:16px;">
                              <tr>
                                <td style="padding:4px 16px;font-size:13px;color:#5a5750;">Subtotal</td>
                                <td style="padding:4px 16px;font-size:13px;color:#5a5750;text-align:right;">₹%s</td>
                              </tr>
                              <tr>
                                <td style="padding:4px 16px;font-size:13px;color:#5a5750;">Shipping</td>
                                <td style="padding:4px 16px;font-size:13px;text-align:right;color:%s;">%s</td>
                              </tr>
                              <tr>
                                <td style="padding:12px 16px 4px;font-size:15px;font-weight:700;color:#2d2a22;border-top:1px solid #dcd9d5;">Total Paid</td>
                                <td style="padding:12px 16px 4px;font-size:15px;font-weight:700;color:#01696f;text-align:right;border-top:1px solid #dcd9d5;">₹%s</td>
                              </tr>
                            </table>
                          </td>
                        </tr>

                        <!-- SHIPPING ADDRESS -->
                        <tr>
                          <td style="padding:0 40px 24px;">
                            <p style="margin:0 0 10px;font-size:14px;font-weight:700;color:#2d2a22;text-transform:uppercase;letter-spacing:0.5px;">📦 Delivering To</p>
                            <div style="background:#f7f6f2;border-radius:8px;padding:14px 16px;font-size:13px;line-height:1.7;color:#5a5750;">
                              <strong style="color:#2d2a22;">%s</strong><br>
                              📞 %s<br>
                              %s, %s, %s – %s
                            </div>
                          </td>
                        </tr>

                        <!-- DELIVERY NOTE -->
                        <tr>
                          <td style="padding:0 40px 24px;text-align:center;">
                            <div style="background:#fffbeb;border:1px solid #fde68a;border-radius:8px;padding:14px;font-size:13px;color:#92400e;">
                              🚚 Your order will be delivered within <strong>3–5 business days</strong>.
                              You can track your order at <a href="https://www.ayezu.com/orders" style="color:#01696f;">ayezu.com/orders</a>
                            </div>
                          </td>
                        </tr>

                        <!-- FOOTER -->
                        <tr>
                          <td style="background:#f7f6f2;padding:20px 40px;text-align:center;border-top:1px solid #ece9e4;">
                            <p style="margin:0 0 6px;font-size:12px;color:#9a9890;">Questions? Reply to this email or WhatsApp us at +91 70010 65007</p>
                            <p style="margin:0;font-size:11px;color:#bab9b4;">© 2026 Ayezu Collection · Pune, Maharashtra</p>
                          </td>
                        </tr>

                      </table>
                    </td></tr>
                  </table>

                </body>
                </html>
                """.formatted(
                order.getOrderNumber(), orderDate,
                paymentBadgeColor, paymentLabel,
                itemRows.toString(),
                order.getSubtotal().toPlainString(),
                order.getShippingCharge().compareTo(BigDecimal.ZERO) == 0 ? "#2d8a4e" : "#5a5750",
                order.getShippingCharge().compareTo(BigDecimal.ZERO) == 0 ? "FREE" : "₹" + order.getShippingCharge().toPlainString(),
                order.getTotalAmount().toPlainString(),
                order.getShippingName(), order.getShippingPhone(),
                order.getShippingAddress(), order.getShippingCity(),
                order.getShippingState(), order.getShippingPinCode()
        );
    }

    private String buildVariantBadges(OrderItem item) {
        StringBuilder badges = new StringBuilder();
        if (item.getSelectedSize() != null && !item.getSelectedSize().isBlank()) {
            badges.append("<span style='font-size:11px;background:#f0ece6;color:#5a5750;padding:2px 7px;border-radius:999px;margin-left:6px;'>"
                    + item.getSelectedSize() + "</span>");
        }
        if (item.getSelectedColor() != null && !item.getSelectedColor().isBlank()) {
            badges.append("<span style='font-size:11px;background:#f0ece6;color:#5a5750;padding:2px 7px;border-radius:999px;margin-left:4px;'>"
                    + item.getSelectedColor() + "</span>");
        }
        return badges.toString();
    }
}
