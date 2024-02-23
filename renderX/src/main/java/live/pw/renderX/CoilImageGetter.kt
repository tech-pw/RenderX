package live.pw.renderX

import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import androidx.compose.ui.unit.Density

import androidx.compose.ui.unit.Dp
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest


class CoilImageGetter(
    private val textView: TextView,
    private val viewWidth: Float,
    private val imgAttributes: Map<String, ImgAttributes>,
    private val imageLoader: ImageLoader = Coil.imageLoader(textView.context),
    private val sourceModifier: ((source: String) -> String)? = null,
    private val density: Density? =null
) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {
        val finalSource = sourceModifier?.invoke(source) ?: source

        val attr = imgAttributes[source]
        val drawablePlaceholder = DrawablePlaceHolder()
        imageLoader.enqueue(ImageRequest.Builder(textView.context).data(finalSource).apply {
            target { drawable ->
                drawablePlaceholder.updateDrawable(drawable, viewWidth, attr, density)
                // invalidating the drawable doesn't seem to be enough...
                textView.text = textView.text
            }
        }.build())
        // Since this loads async, we return a "blank" drawable, which we update
        // later
        return drawablePlaceholder
    }

    @Suppress("DEPRECATION")
    private class DrawablePlaceHolder : BitmapDrawable() {

        private var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            drawable?.draw(canvas)
        }

        fun updateDrawable(drawable: Drawable, viewWidth: Float, attr: ImgAttributes?, density: Density?) {
            this.drawable = drawable

            val width =
                if (attr != null && attr.width > 0)
                    density?.let { with(it) { Dp(attr.width.toFloat()).toPx() }.toInt() } ?: attr.width
                else drawable.intrinsicWidth

            val height = if (attr != null && attr.height > 0)
                density?.let { with(it) { Dp(attr.height.toFloat()).toPx() }.toInt() } ?: attr.height
            else drawable.intrinsicHeight

            var newWidth = width
            var newHeight = height

            if (width > viewWidth) {
                val scale = viewWidth / width
                newWidth = (width * scale).toInt()
                newHeight = (height * scale).toInt()
            }

            drawable.setBounds(0, 0, newWidth, newHeight)
            setBounds(0, 0,newWidth, newHeight)
        }
    }


}
