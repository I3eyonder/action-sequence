package com.hieupt.actionsequence

/**
 * Created by HieuPT on 11/8/2022.
 */
abstract class BaseAction(id: String? = null, suffixId: String? = null) : Action {

    override val id: String = if (id != null) {
        if (suffixId != null) {
            "${id}_${suffixId}"
        } else {
            id
        }
    } else {
        if (suffixId != null) {
            "${super.id}_${suffixId}"
        } else {
            super.id
        }
    }
}