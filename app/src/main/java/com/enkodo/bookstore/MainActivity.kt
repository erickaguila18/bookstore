package com.enkodo.bookstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enkodo.bookstore.adapter.RecyclerAdapter
import com.enkodo.bookstore.api.ApiProduction
import com.enkodo.bookstore.api.response.bestsellers.Results
import com.enkodo.bookstore.api.response.books.BookResponse
import com.enkodo.bookstore.api.service.BookService
import com.enkodo.bookstore.rx.RxAPICallHelper
import com.enkodo.bookstore.rx.RxAPICallback
import es.dmoral.toasty.Toasty
import io.reactivex.Observable
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv_books)

        NoInternetDialogPendulum.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        callServiceBestSellers()
                        callServiceBooks()
                    }
                }

                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                    "Check your Internet connection and try again." // Optional
                showInternetOnButtons = false // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional

                onAirplaneModeTitle = "No Internet" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                pleaseTurnOffText = "Please turn off" // Optional
                airplaneModeOffButtonText = "Airplane mode" // Optional
                showAirplaneModeOffButtons = false // Optional
            }
        }.build()


    }

    private fun callServiceBooks() {
        val mBookService: BookService = ApiProduction(this).provideService(BookService::class.java)
        val apiCall: Observable<BookResponse> = mBookService.getAllBooks()
        RxAPICallHelper().call(apiCall, object : RxAPICallback<BookResponse> {
            override fun onSuccess(items: BookResponse) {
                setBookData(items)
            }

            override fun onFailed(throwable: Throwable) {
                //Empty
            }
        })

    }

    private fun callServiceBestSellers() {
        val mBookService: BookService = ApiProduction(this).provideService(BookService::class.java)
        val apiCall: Observable<Results> = mBookService.getBestSellers()
        RxAPICallHelper().call(apiCall, object : RxAPICallback<Results> {
            override fun onSuccess(items: Results) {
                val bsList: List<String> = items.results.best_sellers

            }

            override fun onFailed(throwable: Throwable) {
                Toasty.error(this@MainActivity, "Failed to retrieve data.").show()
            }
        })

    }


    private fun setBookData(items: BookResponse) {
        recyclerView.layoutManager = LinearLayoutManager(this)

        val recyclerAdapter = RecyclerAdapter()
        recyclerAdapter.setData(items)
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.setOnItemClick(object : RecyclerAdapter.MyAdapterListener {
            override fun onItemViewClick(webUrl: String, position: Int) {

            }
        })
    }
}