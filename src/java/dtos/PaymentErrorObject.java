/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author ngochuu
 */
public class PaymentErrorObject implements Serializable {
    private String recipientNameError, phoneError, addressError, paymentTypeError;

    public PaymentErrorObject() {
    }

    public String getRecipientNameError() {
        return recipientNameError;
    }

    public void setRecipientNameError(String recipientNameError) {
        this.recipientNameError = recipientNameError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getPaymentTypeError() {
        return paymentTypeError;
    }

    public void setPaymentTypeError(String paymentTypeError) {
        this.paymentTypeError = paymentTypeError;
    }
    
}
