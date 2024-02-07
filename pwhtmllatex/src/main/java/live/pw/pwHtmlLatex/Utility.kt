package live.pw.pwHtmlLatex

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object Utility {

    /**
     * Function to determine the font based on the provided [textStyle] or default values.
     *
     * @param textStyle The style of the text.
     * @return The font resource ID.
     */
    fun determineFont(textStyle: TextStyle?): Int {
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
            R.font.roboto_medium_font
        }
    }

    /**
     * Function to determine the font based on the provided [textStyle] or default values.
     *
     * @param textStyle The style of the text.
     * @return The font Name.
     */
    fun determineFontName(textStyle: TextStyle?): String {
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
                   "BalsamiqsansRegular"
                } else {
                   "RobotoMedium"
                }
            } else {
                "GilroyMedium"
            }
        } else {
            "RobotoMedium"
        }
    }



}