package com.example.latexview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.customlatex.CustomLatexView
import com.example.latexview.ui.theme.LatexViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LatexViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    CustomLatexView(
        latex = "<strong>Agriculture</strong> is the cultivation of animals, plants, other life forms for food and other products used to sustain human life."
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LatexViewTheme {
        Greeting()
    }
}