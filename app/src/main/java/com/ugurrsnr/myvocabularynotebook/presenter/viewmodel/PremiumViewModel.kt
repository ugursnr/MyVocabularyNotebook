package com.ugurrsnr.myvocabularynotebook.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ugurrsnr.myvocabularynotebook.premium.MainApplication

class PremiumViewModel(application: Application) : AndroidViewModel(application) {

    private var _offeringsList = MutableLiveData<List<com.revenuecat.purchases.Package>>()
    val offeringsList get() = _offeringsList

    fun getOfferings(){

        val offerings = MainApplication().getOfferings()
        _offeringsList.postValue(offerings)


    }




}