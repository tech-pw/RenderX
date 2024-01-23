package com.example.customlatex

import android.net.ParseException
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import ru.noties.jlatexmath.JLatexMathDrawable
import java.util.regex.Pattern

const val TAG = "CustomLatexView"

@Composable
fun CustomLatexView(
    modifier: Modifier = Modifier,
    latex: String,
    textSize: Float = 16f,
    @ColorInt textColor: Int = Color.Red.toArgb(),
    textStyle: TextStyle? = null,
    loadFromWebView: Boolean = false,
    onError: (message: String) -> Unit = {},
    layer: Int? = null
) {

    val density = LocalDensity.current
    val context = LocalContext.current
    val latexSize = with(density) { Dp(textSize).toPx() }

    val fontFamily = if (textStyle == null) {
        val font = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            /* other text style attributes */
        )
        if (/*LocalTextStyles.current == BrutalismTextStyle*/ true) {
            if (font.fontFamily == BrutalismFontFamilyBalsamiq) {
                R.font.balsamiqsans_regular
            } else {
                R.font.roboto_medium_font
            }
        } else {
            R.font.gilroy_medium
        }
    } else {
        R.font.roboto_light
    }

    var invalidLatex by remember { mutableStateOf(false) }

    if (!invalidLatex && !loadFromWebView) {
        BoxWithConstraints(modifier = modifier) {
            val viewWidth = with(density) { maxWidth.toPx() }

            val textView = remember {
                TextView(context).apply {
                    layoutParams.apply {
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }
                    val imgAttributes = extractAttributesFromImg(latex)
                    val imageGetter = CoilImageGetter(
                        textView = this,
                        viewWidth = viewWidth,
                        imgAttributes = imgAttributes,
                        density = density
                    )

                    try {
                        parseLatex(latex)
                        val tagHandler = TagHandler()
                        val text = HtmlCompat.fromHtml(
                            /* source = */ latex,
                            /* flags = */ HtmlCompat.FROM_HTML_MODE_COMPACT,
                            /* imageGetter = */ imageGetter,
                            /* tagHandler = */ tagHandler
                        )


                        // Regular expression to match LaTeX expressions between $$
                        val latexRegex =
                            Regex("""(?:\$\$(.*?)\$\$)|(?:\\\((.*?)\\\))|(?:\\\[(.*?)\\\])""")
                        val matches = latexRegex.findAll(text)
                        // List to store the start index and LaTeX expression pairs
                        val spans = mutableListOf<Pair<Int, String>>()
                        // Extract LaTeX expressions and their start indices
                        for (match in matches) {
                            val lat = match.groupValues[0]
                            spans.add(Pair(match.range.first, lat))
                        }

                        // Create a SpannableStringBuilder and replace LaTeX expressions with image spans
                        val spannable = SpannableStringBuilder(text)

                        for (span in spans) {
                            val drawable = JLatexMathDrawable.builder(span.second)
                                .color(textColor)
                                .textSize(latexSize)
                                .build()

                            val imageWidth = drawable.intrinsicWidth
                            val imageHeight = drawable.intrinsicHeight

                            if (imageWidth > viewWidth) {
                                val scale = viewWidth / imageWidth.toFloat()
                                val newWidth = imageWidth * scale
                                val newHeight = imageHeight * scale

                                drawable.setBounds(0, 0, newWidth.toInt(), newHeight.toInt())
                            }

                            spannable.setSpan(
                                VerticalImageSpan(drawable),
                                span.first,
                                span.first + span.second.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }


                        this.setTextColor(textColor)
                        this.typeface = ResourcesCompat.getFont(context, fontFamily)
                        this.textSize = textSize
                        this.text = spannable.trim()
                    } catch (e: Exception) {
                        invalidLatex = true
                        onError(e.message.orEmpty())
                        Log.d(TAG, e.message.toString())
                    }
                }
            }

            AndroidView(modifier = Modifier, factory = { textView })
        }
    } else {
        MathView(
            text = latex,
            modifier = modifier,
            textSize = textSize,
            textStyle = textStyle ?: TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        )
    }
}


internal fun extractAttributesFromImg(htmlString: String): MutableMap<String, ImgAttributes> {
    val imgAttributes = mutableMapOf<String, ImgAttributes>()
    val pattern =
        Pattern.compile("<img\\s+[^>]*src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*\\s+width\\s*=\\s*['\"](\\d+)['\"][^>]*\\s+height\\s*=\\s*['\"](\\d+)['\"][^>]*>")

    val matcher = pattern.matcher(htmlString)
    while (matcher.find()) {
        val imgTag = matcher.group()
        val src = matcher.group(1).orEmpty()
        val width = matcher.group(2)?.toIntOrNull() ?: 0
        val height = matcher.group(3)?.toIntOrNull() ?: 0
        imgAttributes[src] = ImgAttributes(
            imgTag,
            matcher.start(),
            src = src,
            width = width,
            height = height
        )
    }

    return imgAttributes
}

@Throws(ParseException::class)
private fun parseLatex(latex: String) {
    val symbols = listOf(
        "rightleftharpoons",
        "nleqslant",
        "Omega",
        "varOmega",
        "otimes",
        "<br"
    )
    var symbol = ""
    symbols.forEachIndexed { index, s ->
        symbol += if (index == 0) {
            s
        } else {
            "|$s"
        }
    }
    if (symbol.isEmpty()) return
    val regex = Regex(symbol)
    val matches = regex.find(latex)

    if (matches?.groupValues?.isNotEmpty() == true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            throw ParseException("PW: Can not parse symbol ${matches.groupValues[0]}")
        }
}


data class ImgAttributes(
    val tag: String = "",
    val pos: Int,
    val src: String,
    val width: Int,
    val height: Int
)

