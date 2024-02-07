package live.pw.latexview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import live.pw.latexview.ui.theme.LatexViewTheme
import live.pw.pwHtmlLatex.PwHtmlLatex

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
                    ExampleView()
                }
            }
        }
    }
}

@Composable
fun ExampleView(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(20) {
            LatexEquationsExample()
        }
        item {
            Text(
                text = "-------------------------------------------------- Math Equations will start from here ---------------------------------------",
                color = Color.Red,
                fontSize = 40.sp
            )
            Divider(thickness = 100.dp)
        }
        items(20) {
            MathMLEquationExample()
        }
    }
}

@Composable
fun LatexEquationsExample() {
    PwHtmlLatex(
        latex = "<h2>LaTeX Equations:</h2>\n" +
                "\n" +
                "  <p>Example 1: Equation 1 \\(e^{i\\pi} + 1 = 0\\)</p>\n" +
                "\n" +
                "  <p>Example 2: Equation with fractions \\[ f(x) = \\frac{1}{2\\pi i} \\oint_C \\frac{f(z)}{z-z_0} \\, dz \\]</p>\n" +
                "<p>Example 3: Quadratic formula \\(x = \\frac{{-b \\pm \\sqrt{{b^2 - 4ac}}}}{{2a}}\\)</p>\n" +
                "\n" +
                "  <p>Example 4: Maxwell's equations\n" +
                "  \\[\n" +
                "  \\begin{aligned}\n" +
                "  \\nabla \\cdot \\mathbf{E} &= \\frac{\\rho}{\\varepsilon_0} \\\\\n" +
                "  \\nabla \\cdot \\mathbf{B} &= 0 \\\\\n" +
                "  \\nabla \\times \\mathbf{E} &= -\\frac{\\partial \\mathbf{B}}{\\partial t} \\\\\n" +
                "  \\nabla \\times \\mathbf{B} &= \\mu_0 \\mathbf{J} + \\mu_0\\varepsilon_0 \\frac{\\partial \\mathbf{E}}{\\partial t}\n" +
                "  \\end{aligned}\n" +
                "  \\]</p>\n" +
                "\n" +
                "  <p>Example 5: Dirac equation \\(\\gamma^\\mu (i\\hbar\\partial_\\mu - e A_\\mu) \\psi - m c \\psi = 0\\)</p>\n"
    )
}

@Composable
fun MathMLEquationExample() {
    PwHtmlLatex(
        latex = "<h2>MathML Equations:</h2>\n" +
                "\n" +
                "  <p>Example 1: Equation 1 <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
                "    <mrow>\n" +
                "      <msup>\n" +
                "        <mi>e</mi>\n" +
                "        <mrow>\n" +
                "          <mi>i</mi>\n" +
                "          <mi>&#x03C0;</mi>\n" +
                "        </mrow>\n" +
                "      </msup>\n" +
                "      <mo>+</mo>\n" +
                "      <mn>1</mn>\n" +
                "      <mo>=</mo>\n" +
                "      <mn>0</mn>\n" +
                "    </mrow>\n" +
                "  </math></p>\n" +
                "\n" +
                "  <p>Example 3: Equation with fractions <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
                "    <mrow>\n" +
                "      <mi>f</mi>\n" +
                "      <mo>(</mo>\n" +
                "      <mi>x</mi>\n" +
                "      <mo>)</mo>\n" +
                "      <mo>=</mo>\n" +
                "      <mfrac>\n" +
                "        <mn>1</mn>\n" +
                "        <mrow>\n" +
                "          <mn>2</mn>\n" +
                "          <mi>&#x03C0;</mi>\n" +
                "          <mi>i</mi>\n" +
                "        </mrow>\n" +
                "      </mfrac>\n" +
                "      <mo>&#x222E;</mo>\n" +
                "      <mo>(</mo>\n" +
                "      <mi mathvariant=\"normal\">C</mi>\n" +
                "      <mo>)</mo>\n" +
                "      <mfrac>\n" +
                "        <mrow>\n" +
                "          <mi>f</mi>\n" +
                "          <mo>(</mo>\n" +
                "          <mi>z</mi>\n" +
                "          <mo>)</mo>\n" +
                "        </mrow>\n" +
                "        <mrow>\n" +
                "          <mi>z</mi>\n" +
                "          <mo>&#x2212;</mo>\n" +
                "          <mi>z</mi>\n" +
                "          <sub>\n" +
                "            <mn>0</mn>\n" +
                "          </sub>\n" +
                "        </mrow>\n" +
                "      </mfrac>\n" +
                "      <mi>d</mi>\n" +
                "      <mi>z</mi>\n" +
                "    </mrow>\n" +
                "  </math></p>" +
                " <p>Example 1: Quadratic formula <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
                "    <mrow>\n" +
                "      <mi>x</mi>\n" +
                "      <mo>=</mo>\n" +
                "      <mfrac>\n" +
                "        <mrow>\n" +
                "          <mo>&#x2212;</mo>\n" +
                "          <mi>b</mi>\n" +
                "          <mo>&#xB1;</mo>\n" +
                "          <msqrt>\n" +
                "            <msup>\n" +
                "              <mi>b</mi>\n" +
                "              <mn>2</mn>\n" +
                "            </msup>\n" +
                "            <mo>&#x2212;</mo>\n" +
                "            <mn>4</mn>\n" +
                "            <mi>a</mi>\n" +
                "            <mi>c</mi>\n" +
                "          </msqrt>\n" +
                "        </mrow>\n" +
                "        <mrow>\n" +
                "          <mn>2</mn>\n" +
                "          <mi>a</mi>\n" +
                "        </mrow>\n" +
                "      </mfrac>\n" +
                "    </mrow>\n" +
                "  </math></p>\n" +
                "\n" +
                "  <p>Example 4: Maxwell's equations\n" +
                "  <math xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\">\n" +
                "    <mrow>\n" +
                "      <mtable>\n" +
                "        <mtr>\n" +
                "          <mtd>\n" +
                "            <mi>&#x2207;</mi>\n" +
                "            <mo>&#xB7;</mo>\n" +
                "            <mi mathvariant=\"bold\">E</mi>\n" +
                "            <mo>=</mo>\n" +
                "            <mfrac>\n" +
                "              <mi>&#x03C1;</mi>\n" +
                "              <mi>&#x03B5;</mi>\n" +
                "              <mn>0</mn>\n" +
                "            </mfrac>\n" +
                "          </mtd>\n" +
                "        </mtr>\n" +
                "        <mtr>\n" +
                "          <mtd>\n" +
                "            <mi>&#x2207;</mi>\n" +
                "            <mo>&#xB7;</mo>\n" +
                "            <mi mathvariant=\"bold\">B</mi>\n" +
                "            <mo>=</mo>\n" +
                "            <mn>0</mn>\n" +
                "          </mtd>\n" +
                "        </mtr>\n" +
                "        <mtr>\n" +
                "          <mtd>\n" +
                "            <mi>&#x2207;</mi>\n" +
                "            <mo>&#xD7;</mo>\n" +
                "            <mi mathvariant=\"bold\">E</mi>\n" +
                "            <mo>=</mo>\n" +
                "            <mo>&#x2212;</mo>\n" +
                "            <mfrac>\n" +
                "              <mi>&#x2202;</mi>\n" +
                "              <mi>&#x2202;</mi>\n" +
                "              <mi>t</mi>\n" +
                "              <mi mathvariant=\"bold\">B</mi>\n" +
                "            </mfrac>\n" +
                "          </mtd>\n" +
                "        </mtr>\n" +
                "        <mtr>\n" +
                "          <mtd>\n" +
                "            <mi>&#x2207;</mi>\n" +
                "            <mo>&#xD7;</mo>\n" +
                "            <mi mathvariant=\"bold\">B</mi>\n" +
                "            <mo>=</mo>\n" +
                "            <mi>&#x03BC;</mi>\n" +
                "            <mn>0</mn>\n" +
                "            <mi mathvariant=\"bold\">J</mi>\n" +
                "            <mo>+</mo>\n" +
                "            <mi>&#x03BC;</mi>\n" +
                "            <mi>&#x03B5;</mi>\n" +
                "            <mn>0</mn>\n" +
                "            <mfrac>\n" +
                "              <mi>&#x2202;</mi>\n" +
                "              <mi>&#x2202;</mi>\n" +
                "              <mi>t</mi>\n" +
                "              <mi mathvariant=\"bold\">E</mi>\n" +
                "            </mfrac>\n" +
                "          </mtd>\n" +
                "        </mtr>\n" +
                "      </mtable>\n" +
                "    </mrow>\n" +
                "  </math></p>\n" +
                "\n" +
                "  <p>Example 5: Dirac equation <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
                "    <mrow>\n" +
                "      <msup>\n" +
                "        <mi>&#x03B3;</mi>\n" +
                "        <mi>&#x03BC;</mi>\n" +
                "      </msup>\n" +
                "      <mo>(</mo>\n" +
                "      <mi>i</mi>\n" +
                "      <mi>&#x210F;</mi>\n" +
                "      <mrow>\n" +
                "        <mi>&#x2202;</mi>\n" +
                "        <mi>&#x03BC;</mi>\n" +
                "      </mrow>\n" +
                "      <mo>&#x2212;</mo>\n" +
                "      <mi>e</mi>\n" +
                "      <mi>A</mi>\n" +
                "      <msub>\n" +
                "        <mi>&#x03BC;</mi>\n" +
                "        <mi>&#x03BC;</mi>\n" +
                "      </msub>\n" +
                "      <mo>)</mo>\n" +
                "      <mi>&#x03C8;</mi>\n" +
                "      <mo>&#x2212;</mo>\n" +
                "      <mi>m</mi>\n" +
                "      <mi>c</mi>\n" +
                "      <mi>&#x03C8;</mi>\n" +
                "      <mo>=</mo>\n" +
                "      <mn>0</mn>\n" +
                "    </mrow>\n" +
                "  </math></p>"
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LatexViewTheme {
        ExampleView()
    }
}