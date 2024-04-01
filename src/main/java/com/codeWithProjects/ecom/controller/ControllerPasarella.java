package com.codeWithProjects.ecom.controller;

import com.mercadopago.*;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class ControllerPasarella {

    @PostMapping("/proccess-payment")

    public ResponseEntity<?> createPayment (@RequestBody PaymentRequest paymentRequest) {
        MercadoPagoConfig.setAccessToken("TEST-5222133002454186-031221-9233c0d8e552ec1c09de40b87b51334c-1723211973");

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest createRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(paymentRequest.getTransactionAmount())
                        .token(paymentRequest.getToken())
                        .description(paymentRequest.getDescription())
                        .installments(paymentRequest.getInstallments())
                        .paymentMethodId(paymentRequest.getPaymentMethodId())
                        .payer(PaymentPayerRequest.builder().email(paymentRequest.getEmail()).build())
                        .build();

        try {
            Payment payment = client.create(createRequest);
            return ResponseEntity.ok(payment);
        } catch (MPApiException ex) {
            return ResponseEntity.status(ex.getApiResponse().getStatusCode()).body(ex.getApiResponse().getContent());
        } catch (MPException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor.");
        }
    }

    public static class PaymentRequest {
        private BigDecimal transactionAmount;
        private String token;
        private String description;
        private int installments;
        private String paymentMethodId;
        private String email;

        // Getters and setters
        // Implementar los métodos getter y setter para los atributos
        // transactionAmount, token, description, installments, paymentMethodId y email
        public BigDecimal getTransactionAmount() {
            return transactionAmount;
        }

        public void setTransactionAmount(BigDecimal transactionAmount) {
            this.transactionAmount = transactionAmount;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getInstallments() {
            return installments;
        }

        public void setInstallments(int installments) {
            this.installments = installments;
        }

        public String getPaymentMethodId() {
            return paymentMethodId;
        }

        public void setPaymentMethodId(String paymentMethodId) {
            this.paymentMethodId = paymentMethodId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}
