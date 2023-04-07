package com.ugurrsnr.myvocabularynotebook.my_premium

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import com.ugurrsnr.myvocabularynotebook.premium.BillingClientWrapper

class MyBillingClient (context: Context
) : PurchasesUpdatedListener, ProductDetailsResponseListener {

    private val billingClient = BillingClient.newBuilder(context)
        .setListener(this)
        .enablePendingPurchases()
        .build()

    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {
        TODO("Not yet implemented")
    }

    override fun onProductDetailsResponse(billingResult: BillingResult, productDetailsList: MutableList<ProductDetails>) {
        val responseCode = billingResult.responseCode
        val debugMessage = billingResult.debugMessage
        when (responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                var newMap = emptyMap<String, ProductDetails>()
                if (productDetailsList.isNullOrEmpty()) {
                    Log.e(
                        MyBillingClient.TAG,
                        "onProductDetailsResponse: " +
                                "Found null or empty ProductDetails. " +
                                "Check to see if the Products you requested are correctly " +
                                "published in the Google Play Console."
                    )
                } else {
                    newMap = productDetailsList.associateBy {
                        it.productId
                    }
                }
                _productWithProductDetails.value = newMap
            }
            else -> {
                Log.i(BillingClientWrapper.TAG, "onProductDetailsResponse: $responseCode $debugMessage")
            }
        }
    }


}