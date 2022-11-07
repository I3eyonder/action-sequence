package com.hieupt.actionsequence

/**
 * Created by HieuPT on 11/6/2022.
 */
interface Action {
    val id: Int
        get() = -1

    suspend fun doAction()
}