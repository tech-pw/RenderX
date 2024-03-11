[![](https://jitpack.io/v/tech-pw/RenderX.svg)](https://jitpack.io/#tech-pw/RenderX)

**Title: An Native Android library to render latex with HTML support**

**Introduction:**
In the realm of Android development, the struggle with sluggish equation rendering has been all too real. RenderX steps into the spotlight as a game-changing solution, not only accelerating LaTeX rendering but also seamlessly handling HTML with a nuanced fallback mechanism. 

**The Dual Power of RenderX:**

1. **Native Rendering Excellence:**
    - Bid farewell to the performance bottlenecks associated with webviews. RenderX champions native rendering, dramatically enhancing the speed of LaTeX equation display on Android. Our benchmark tests reveal a 50-80% improvement, translating to a more responsive user experience.
    So we have captured the frame rate graph while rendering Latex equations in RenderX vs MathJax. The difference can be visually seen.
    
<div style="display: flex;">
    <img src="https://raw.githubusercontent.com/tech-pw/RenderX/aaa31df197a3320aea3a868b97d79b88038b3f5b/screenshots/Screenshot%202024-02-16%20at%203.34.50%20PM.png" alt="Latex rendering" style="width: 45%;">
    <img src="https://raw.githubusercontent.com/tech-pw/RenderX/aaa31df197a3320aea3a868b97d79b88038b3f5b/screenshots/Screenshot%202024-02-16%20at%203.35.03%20PM.png" alt="MathJax Rendering" style="width: 45%;">
</div>

https://github.com/tech-pw/RenderX/assets/160108670/65903362-d56b-4a3a-bb7c-776dd3856c4f


2. **HTML + LaTeX Synergy:**
    - One of RenderX's standout features is its ability to seamlessly render LaTeX embedded within HTML. This means developers can pass HTML with LaTeX equations directly, unleashing a new realm of possibilities. To illustrate its prowess, we've included real-world scenarios where HTML and LaTeX coexist harmoniously.

```markdown
Example 

RenderX(
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
```
Output 
![Rendeing screenshot](https://raw.githubusercontent.com/tech-pw/RenderX/bfb85a776e0a7d2e9a39f5e87666b7246e2fce5e/screenshots/Screenshot%202024-02-16%20at%204.39.23%20PM.png)

3. **Versatile HTML Rendering:**
    - RenderX isn't confined to LaTeX alone; it excels in rendering pure HTML content with equal finesse with support of rendeing images via coil. Explore scenarios where the library effortlessly handles diverse HTML content, making it a versatile choice for rendering beyond mathematical equations.

4. **Intelligent Fallback Mechanism:**
    - In complex scenarios where native rendering faces challenges, RenderX gracefully falls back to webview, so that it can be used in production ready usecases, ensuring a seamless user experience. 

**Integration Guide: Bringing pwHtml Latex Into Your Project:**
1) For new project system following has to be added to setting.gradle and for old system it has to be added to root level build.gradle

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ........... other repo
        mavenCentral()
        maven("https://jitpack.io")

    }
}
```

2) Then add the dependency to the app level build.gradle

```	
dependencies {
	        implementation 'com.github.tech-pw:RenderX:$version'
	}

```



