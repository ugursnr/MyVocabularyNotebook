package com.ugurrsnr.myvocabularynotebook.presenter.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.*
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentHomeBinding
import com.ugurrsnr.myvocabularynotebook.presenter.adapter.VocabulariesHomeAdapter
import com.ugurrsnr.myvocabularynotebook.presenter.viewmodel.AddVocabularySharedViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.ugurrsnr.myvocabularynotebook.premium.Security
import com.ugurrsnr.myvocabularynotebook.presenter.MainActivity
import java.io.IOException


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var vocabularyAdapter = VocabulariesHomeAdapter()
    private lateinit var sharedViewModel: AddVocabularySharedViewModel

    lateinit var mAdView : AdView

    //Purchase

    private lateinit var billingClient : BillingClient
    private lateinit var productDetails : ProductDetails
    private var selectedOfferToken : String? = null

    private var isSuccess = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdView= binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //sharedViewModel = ViewModelProvider(this)[AddVocabularySharedViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity()).get(AddVocabularySharedViewModel::class.java)
        sharedViewModel.getAllVocabulariesFromDB()
        prepareRecyclerView()
        observeAllVocabularies()

        addVocabularyOnClick()

        vocabularyAdapter.apply {
            onItemDeleteClicked = {
                sharedViewModel.deleteVocabulary(it)
                sharedViewModel.getAllVocabulariesFromDB()
                observeAllVocabularies()
            }
            onItemClicked = {
                val action = HomeFragmentDirections.actionHomeFragmentToAddVocabularyBottomSheetFragment()
                action.vocabularyID = it.vocabularyID
                Navigation.findNavController(requireView()).navigate(action)
            }
        }


        //purchase
        billingClient = BillingClient.newBuilder(requireContext())
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()



        subscribeBtn()
    }



    private fun subscribeBtn() {
        binding.startPremiumBtn.setOnClickListener {

            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingServiceDisconnected() {
                    TODO("Not yet implemented")
                }

                override fun onBillingSetupFinished(billingResult: BillingResult) {

                    val productList = listOf(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId("vocabulary_notebook") //product id in play console
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build()
                    )

                    val params = QueryProductDetailsParams.newBuilder()
                        .setProductList(productList)
                    billingClient.queryProductDetailsAsync(params.build()) { billingResult, productDetailsList ->

                        for (productDetails in productDetailsList) {

                            val offerToken =
                                productDetails.subscriptionOfferDetails?.get(0)?.offerToken

                            val productDetailsParamsList = listOf(offerToken?.let {
                                BillingFlowParams.ProductDetailsParams.newBuilder()
                                    .setProductDetails(productDetails)
                                    .setOfferToken(it)
                                    .build()
                            })

                            val billingFlowParams = BillingFlowParams.newBuilder()
                                .setProductDetailsParamsList(productDetailsParamsList)
                                .build()
                            val billingResult = billingClient.launchBillingFlow(
                                activity as MainActivity,
                                billingFlowParams
                            )
                        }

                    }
                }

            })
        }
    }

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, Purchase ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && Purchase != null){
                for (purchase in Purchase){
                    handlePurchase(purchase)

                }
            }else if(billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED){
                binding.subscriptionStatusTV.text = "Subscribed"
                isSuccess = true

            }else if (billingResult.responseCode == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED){
                binding.subscriptionStatusTV.text = "Feature Not Supported"

            }else{
                Toast.makeText(requireContext(),"Error : ${billingResult.debugMessage}",Toast.LENGTH_LONG).show()
                Log.d("Premium","Error : ${billingResult.debugMessage}")
            }
        }
    private fun handlePurchase(purchase: Purchase){
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        val listener = ConsumeResponseListener{billingResult, s ->
            if ( billingResult.responseCode == BillingClient.BillingResponseCode.OK){


            }
        }
        billingClient.consumeAsync(consumeParams, listener)
        if(purchase.purchaseState == Purchase.PurchaseState.PURCHASED){

            if(!verifyValidSignature(purchase.originalJson,purchase.signature)){
                Toast.makeText(requireContext(), "Error Invalid purchase", Toast.LENGTH_LONG).show()
                Log.d("Premium", "Error Invalid purchase")
                return
            }
            if(!purchase.isAcknowledged){
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                billingClient.acknowledgePurchase(acknowledgePurchaseParams,acknowledgePurchaseResponseListener)
                binding.subscriptionStatusTV.text = "Subscribed"
                isSuccess = true
            }else{
                binding.subscriptionStatusTV.text = "Already Subscribed"
            }
        }else if(purchase.purchaseState == Purchase.PurchaseState.PENDING){
            binding.subscriptionStatusTV.text = "PENDING"

        }else if(purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE){
            binding.subscriptionStatusTV.text = "UNSPECIFIED_STATE"

        }

    }
    var acknowledgePurchaseResponseListener = AcknowledgePurchaseResponseListener { billingResult ->

        if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){
            binding.subscriptionStatusTV.text = "Subscribed"
            isSuccess = true
        }

    }

    private fun verifyValidSignature(signedData : String, signature : String) : Boolean{
        return try {
            val base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnQfc1GrGnkV3+ZmRkdN3O6SLYAErmTzZIyibuZxArWphLPkwlDxBryjTNXeEG7Erl5jJfPJpeMv9l5UsRsYqn+Hvzc7ZqwK6IUIn9GY0DDdqVLCw6Iaet0qQ3z1wozcBq7BFiE/uNaZj8eyhpNUWwGeA1qWErW5JGHBuswf4fgZ6Q+aNg9d89/1XBPpDcDGQuRm0W8mYFRYlcyELZgU9BM/pb29cv4Hp4N1SK7T8LgLoiAS4zWw7uKC9clmpMDO04ibF+yh01vbkoWDGWBwXxopwJBf2AbbRHumi6Qyaxz+y/icWmelv1scKK4lHccxmdRSnmoxB2wtz6fhmNPaMwQIDAQAB"
            val security = Security()
            security.verifyPurchase(base64Key,signedData,signature)
        }catch (e : IOException){
            false
        }

    }



    private fun prepareRecyclerView(){

        binding.recyclerViewHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = vocabularyAdapter
        }
    }

    private fun addVocabularyOnClick(){
        binding.addVocabularyButton.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAddVocabularyBottomSheetFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeAllVocabularies(){

        sharedViewModel.allVocabulariesLiveData.observe(viewLifecycleOwner, Observer {

            vocabularyAdapter.updateVocabularyList(it)

        })
    }


}
