package com.teamblue.WeBillv2.controller;

import android.content.Context;

import com.stripe.android.paymentsheet.PaymentSheet;
import com.teamblue.WeBillv2.service.StripeService;

public class StripeController {

    StripeService stripeService = new StripeService();

//    public void initializePaymentSheet(Context context, PaymentSheet paymentSheet) {
//        stripeService.getPaymentSheet(context, paymentSheet);
//    }
}
