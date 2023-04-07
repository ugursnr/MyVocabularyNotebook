package com.ugurrsnr.myvocabularynotebook.presenter.view

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.ugurrsnr.myvocabularynotebook.presenter.MainActivity


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var vocabularyAdapter = VocabulariesHomeAdapter()
    private lateinit var sharedViewModel: AddVocabularySharedViewModel

    lateinit var mAdView : AdView

    //Purchase
    /*
    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
        }


     */
    //private lateinit var billingClient : BillingClient


    //private lateinit var productDetails : ProductDetails
    //private var selectedOfferToken : String? = null

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
        /*
        billingClient = BillingClient.newBuilder(requireContext())
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                }
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
        //subscribeBtn()

         */

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

    /*
    private fun subscribeBtn(){
        binding.startPremiumBtn.setOnClickListener {

            val queryProductDetailsParams =
                QueryProductDetailsParams.newBuilder()
                    .setProductList(
                        arrayListOf(
                            QueryProductDetailsParams.Product.newBuilder()
                                .setProductId("vocabulary_notebook")
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build())
                    )
                    .build()

            billingClient.queryProductDetailsAsync(queryProductDetailsParams) {
                    billingResult,
                    productDetailsList ->

                productDetails = productDetailsList.first()

                println("billingResult : $billingResult")
                println("productDetailsList : $productDetailsList")


            }

            selectedOfferToken = productDetails.subscriptionOfferDetails?.let {
                it.first().offerToken
            }

            val productDetailsParamsList = listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                    .setProductDetails(productDetails)
                    // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                    // for a list of offers that are available to the user
                    .setOfferToken(selectedOfferToken!!)
                    .build()
            )

            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build()

// Launch the billing flow
            val billingResult = billingClient.launchBillingFlow(activity as MainActivity, billingFlowParams)



        }
    }



*/

}
