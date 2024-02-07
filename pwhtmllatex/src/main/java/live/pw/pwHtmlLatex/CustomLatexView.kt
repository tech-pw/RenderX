package live.pw.pwHtmlLatex

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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import org.scilab.forge.jlatexmath.ParseException
import ru.noties.jlatexmath.JLatexMathDrawable
import java.util.regex.Pattern

/**
 * Tag for logging debug information in this library.
 */
private const val TAG = "EquationView"

/**
 * Composable function to render LaTeX equations in a custom view.
 *
 * @param modifier The Compose modifier for customization.
 * @param latex The LaTeX equation to be rendered.
 * @param textSize The size of the text in sp.
 * @param textColor The color of the text.
 * @param textStyle The style of the text.
 * @param loadFromWebView Whether to load the LaTeX from a WebView (if true, ignores other parameters).
 * @param onError Callback to handle errors during rendering.
 * @param layer The layer to render the LaTeX on.
 */
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
    // Access the current display density and context
    val density = LocalDensity.current
    val context = LocalContext.current
    val latexSize = with(density) { Dp(textSize).toPx() }

    // Determine the font based on the provided textStyle or default values
    val fontFamily = determineFont(textStyle)

    // State to track invalid LaTeX
    var invalidLatex by remember { mutableStateOf(false) }

    // Render LaTeX either as an AndroidView or using MathView
    if (!invalidLatex && !loadFromWebView) {
        BoxWithConstraints(modifier = modifier) {
            val viewWidth = with(density) { maxWidth.toPx() }

            // Create a TextView for rendering LaTeX
            val textView = remember {
                TextView(context).apply {
                    layoutParams.apply {
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }
                }
            }

            // Use AndroidView to integrate the TextView into Compose
            AndroidView(
                modifier = Modifier,
                factory = { textView },
                update = { view ->
                    // Set properties of the TextView
                    view.setTextColor(textColor)
                    view.typeface = ResourcesCompat.getFont(context, fontFamily)
                    view.textSize = textSize
                    // Convert LaTeX to SpannableStringBuilder and set it to the TextView
                    view.text = convertLatexHtmlToSpannable(
                        latex = latex,
                        view = view,
                        density = density,
                        textColor = textColor,
                        viewWidth = viewWidth,
                        latexSize = latexSize,
                        onError = {
                            invalidLatex = true
                            onError(it)
                        }
                    ).trim()
                }
            )
        }
    } else {
        // If invalid LaTeX or loading from WebView, use MathView
        MathView(
            text = latex,
            modifier = modifier,
            textSize = textSize,
            textStyle = textStyle ?: TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            ),
            layer = layer
        )
    }
}

/**
 * Function to determine the font based on the provided [textStyle] or default values.
 *
 * @param textStyle The style of the text.
 * @return The font resource ID.
 */
private fun determineFont(textStyle: TextStyle?): Int {
    return if (textStyle == null) {
        // Define default font style
        val font = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            /* other text style attributes */
        )
        // Choose font based on text style or default values
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
}

/**
 * Function to convert LaTeX and HTML to SpannableStringBuilder.
 *
 * @param latex The LaTeX equation to be converted.
 * @param view The TextView used for rendering.
 * @param density The display density.
 * @param textColor The color of the text.
 * @param viewWidth The width of the rendering view.
 * @param latexSize The size of the LaTeX text.
 * @param onError Callback to handle errors during rendering.
 * @return The SpannableStringBuilder containing the rendered text.
 */
private fun convertLatexHtmlToSpannable(
    latex: String,
    view: TextView,
    density: Density,
    textColor: Int,
    viewWidth: Float,
    latexSize: Float,
    onError: (message: String) -> Unit
): SpannableStringBuilder {
    return try {
        // Extract image attributes from LaTeX
        val imgAttributes = extractAttributesFromImg(latex)
        // Create CoilImageGetter to handle image loading
        val imageGetter = CoilImageGetter(
            textView = view,
            viewWidth = viewWidth,
            imgAttributes = imgAttributes,
            density = density
        )

        // Parse LaTeX and HTML
        parseLatex(latex)
        val tagHandler = TagHandler()
        val text = HtmlCompat.fromHtml(
            /* source = */ latex,
            /* flags = */ HtmlCompat.FROM_HTML_MODE_COMPACT,
            /* imageGetter = */ imageGetter,
            /* tagHandler = */ tagHandler
        )

        // Regular expression to match LaTeX expressions between $$, \( and \[
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
            // Create JLatexMathDrawable for LaTeX rendering
            val drawable = JLatexMathDrawable.builder(span.second)
                .color(textColor)
                .textSize(latexSize)
                .build()

            // Adjust size if image width exceeds the view width
            val imageWidth = drawable.intrinsicWidth
            val imageHeight = drawable.intrinsicHeight

            if (imageWidth > viewWidth) {
                val scale = viewWidth / imageWidth.toFloat()
                val newWidth = imageWidth * scale
                val newHeight = imageHeight * scale

                drawable.setBounds(0, 0, newWidth.toInt(), newHeight.toInt())
            }

            // Set image span in the SpannableStringBuilder
            spannable.setSpan(
                VerticalImageSpan(drawable),
                span.first,
                span.first + span.second.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        spannable
    } catch (e: Exception) {
        Log.d(TAG, e.message.toString())
        onError(e.message.orEmpty())
        SpannableStringBuilder()
    }
}

/**
 * Function to extract attributes from image tags in HTML.
 *
 * @param htmlString The HTML string containing image tags.
 * @return A map of image source URLs to their attributes.
 */
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

/**
 * Function to parse LaTeX and throw an exception for unsupported symbols.
 *
 * @param latex The LaTeX equation to be parsed.
 * @throws ParseException if an unsupported symbol is found.
 */
@Throws(ParseException::class)
internal fun parseLatex(latex: String) {
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
        throw ParseException("PW: Can not parse symbol ${matches.groupValues[0]}")
}

/**
 * Data class to hold attributes of images in LaTeX.
 *
 * @property tag The image tag.
 * @property pos The position of the image in the LaTeX string.
 * @property src The source URL of the image.
 * @property width The width of the image.
 * @property height The height of the image.
 */
data class ImgAttributes(
    val tag: String = "",
    val pos: Int,
    val src: String,
    val width: Int,
    val height: Int
)
