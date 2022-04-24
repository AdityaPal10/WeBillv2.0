package com.teamblue.WeBillv2.controller;

import android.content.Context;

import com.stripe.android.paymentsheet.PaymentSheet;
import com.teamblue.WeBillv2.model.pojo.User;
import com.teamblue.WeBillv2.service.StripeService;

public class StripeController {

    StripeService stripeService = new StripeService();

    public void createStripeAccounts(Context context, String username) {
        User user = new User(username);
        stripeService.createAccount(context, user);
    }

    public void getStripeAccounts(Context context, String username) {
        stripeService.getAccount(context, username);
    }

    public void initPaymentSheet(Context context, PaymentSheet paymentSheet, String username) {
        stripeService.getPaymentSheet(context, paymentSheet, username);
    }
}
