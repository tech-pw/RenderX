package live.pw.pwHtmlLatex

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import live.pw.pwHtmlLatex.mathJaxView.MathJaxViewV1

@Composable
fun MathView(
    modifier: Modifier = Modifier,
    text: String,
    textSize: Float = 16f,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    ),
    textColor: Color = Color.Black,
    layer: Int? = null
) {
    val fontFamily = if (/*LocalTextStyles.current == BrutalismTextStyle*/ true) {
        if (/*textStyle.fontFamily == BrutalismFontFamilyBalsamiq*/  true) {
            "BalsamiqsansRegular"
        } else {
            "RobotoMedium"
        }
    } else {
        "GilroyMedium"
    }
    val colorHex = "#" + Integer.toHexString(textColor.toArgb()).substring(2)
    AndroidView(modifier = modifier, factory = {
        MathJaxViewV1(it, null).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layer?.let { this.setLayerType(layer, null) }
            isVerticalScrollBarEnabled = false
            settings.setSupportZoom(false)
            setPadding(0, 0, 0, 0)
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            WebView.setWebContentsDebuggingEnabled(true)
            this.setText(
                """
                 @font-face {
                    font-family: 'GilroyMedium';
                    src: url("file:///android_res/font/gilroy_medium.otf")
                 }
                 @font-face {
                    font-family: 'BalsamiqsansBold';
                    src: url("file:///android_res/font/balsamiqsans_bold.ttf")
                 }
                 @font-face {
                    font-family: 'BalsamiqsansRegular';
                    src: url("file:///android_res/font/balsamiqsans_regular.ttf")
                 }
                 @font-face {
                    font-family: 'RobotoBold';
                    src: url("file:///android_res/font/roboto_bold_font.ttf")
                 }
                 @font-face {
                    font-family: 'RobotoMedium';
                    src: url("file:///android_res/font/roboto_medium_font.ttf")
                 }
                 body {
                    font-family: '$fontFamily';
                    font-size: ${textSize}px;
                    line-height: ${textSize + 5}px;
                    color: ${colorHex};
                  }
            """.trimIndent(), text
            )
        }
    })
}