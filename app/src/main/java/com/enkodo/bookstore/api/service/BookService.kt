package com.enkodo.bookstore.api.service

import com.enkodo.bookstore.api.response.bestsellers.Results
import com.enkodo.bookstore.api.response.books.BookResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface BookService {
    @GET("books.json")
    fun getAllBooks(): Observable<BookResponse>


    @GET("best_sellers.json")
    fun getBestSellers(): Observable<Results>

}