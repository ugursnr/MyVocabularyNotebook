package com.ugurrsnr.myvocabularynotebook.premium

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener

class BillingClientWrapper(context: Context) : PurchasesUpdatedListener {

    //1. Initialize billingClient
    private val billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases()
            .build()



    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {
        TODO("Not yet implemented")
    }


}