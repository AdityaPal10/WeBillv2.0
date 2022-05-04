package com.teamblue.WeBillv2.controller;

import android.app.Activity;
import android.content.Context;

import com.stripe.android.paymentsheet.PaymentSheet;
import com.teamblue.WeBillv2.model.pojo.PayFriendModel;
import com.teamblue.WeBillv2.model.pojo.User;
import com.teamblue.WeBillv2.service.StripeService;

// intermediary or "manager" between StripeService and the activities that need to call the
// Stripe API
public class StripeController {

    StripeService stripeService = new StripeService();

    public void createStripeAccounts(Context context, String username) {
        User user = new User(username);
        stripeService.createAccount(context, user);
    }

    public void getStripeAccounts(Context context, String username, String mode) {
        stripeService.getAccount(context, username, mode);
    }

    public void initPaymentSheet(Context context, String username, String cusID) {
        stripeService.getPaymentSheet(context, username, cusID);
    }

    public void stripeTransaction(Context context, String srcCustomer, String destAccount, int amt) {
        PayFriendModel payFriendModel = new PayFriendModel(srcCustomer, destAccount, amt);
        stripeService.payFriend(context, payFriendModel);
    }
}
