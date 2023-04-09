package com.ugurrsnr.myvocabularynotebook.premium

import android.app.Application
import android.util.Log
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.getOfferingsWith

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration.Builder(this, "goog_ZdPsEFeICMZFhlSSpcGqAHkCWAq").build())

    }

    fun getOfferings() : List<com.revenuecat.purchases.Package>{
        var packages = listOf<com.revenuecat.purchases.Package>()
        Purchases.sharedInstance.getOfferingsWith({ error ->
            // An error occurred
            Log.d("PremiumLog", "Error occured when fetching offerings : $error")
        }) { offerings ->
            offerings.current?.availablePackages?.takeUnless {
                it.isNullOrEmpty() }?.let {
                packages = it
            }
        }
        return packages
    }
}