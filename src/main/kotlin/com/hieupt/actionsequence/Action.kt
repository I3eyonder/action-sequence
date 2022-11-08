package com.hieupt.actionsequence

/**
 * Created by HieuPT on 11/6/2022.
 */
interface Action {
    val id: String
        get() = this::class.java.simpleName

    suspend fun doAction()
}