package com.example.customlatex

import android.text.Editable
import android.text.Html
import org.xml.sax.XMLReader
import java.text.ParseException

class TagHandler : Html.TagHandler {

    override fun handleTag(
        opening: Boolean,
        tag: String?,
        output: Editable?,
        xmlReader: XMLReader?
    ) {
        if (tag == "math") {
            throw ParseException(
                "mathML does not supported by CustomLatexView",
                Thread.currentThread().stackTrace.firstOrNull()?.lineNumber ?: 0
            )
        }
    }
}

