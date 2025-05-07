package com.example.tyw.utils

import java.util.Locale



object LanguageUtils {

    private val CZECH = "cs"
    private val ENG = "eng"

    fun isLanguageCzech(): Boolean{
        val language = Locale.getDefault().language
        return language.equals(CZECH) || language.equals(ENG)
    }

}