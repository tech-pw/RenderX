[![](https://jitpack.io/v/tech-pw/RenderX.svg)](https://jitpack.io/#tech-pw/RenderX)

# Native Android library to render latex with HTML support

## Introduction
In the realm of Android development, the struggle with sluggish equation rendering has been all too real. RenderX steps into the spotlight as a game-changing solution, not only accelerating LaTeX rendering but also seamlessly handling HTML with a nuanced fallback mechanism. 

#### The Dual Power of RenderX

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

## Integration Guide: Bringing pwHtml Latex Into Your Project:
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

# Contributing

When contributing to this repository, please first discuss the change you wish to make via issue, or any other method with the owners of this repository before making a change.

Please note we have a code of conduct, please follow it in all your interactions with the project.

## Pull Request Process

1. Ensure any install or build dependencies are removed before the end of the layer when doing a
   build.
2. Update the README.md with details of changes to the interface, this includes new environment
   variables, exposed ports, useful file locations and container parameters.
3. You may merge the Pull Request in once you have the sign-off of two other developers, or if you
   do not have permission to do that, you may request the second reviewer to merge it for you.

## Code of Conduct

### Our Pledge

In the interest of fostering an open and welcoming environment, we as
contributors and maintainers pledge to making participation in our project and
our community a harassment-free experience for everyone, regardless of age, body
size, disability, ethnicity, gender identity and expression, level of experience,
nationality, personal appearance, race, religion, or sexual identity and
orientation.

### Our Standards

Examples of behavior that contributes to creating a positive environment
include:

* Using welcoming and inclusive language
* Being respectful of differing viewpoints and experiences
* Gracefully accepting constructive criticism
* Focusing on what is best for the community
* Showing empathy towards other community members

Examples of unacceptable behavior by participants include:

* The use of sexualized language or imagery and unwelcome sexual attention or
advances
* Trolling, insulting/derogatory comments, and personal or political attacks
* Public or private harassment
* Publishing others' private information, such as a physical or electronic
  address, without explicit permission
* Other conduct which could reasonably be considered inappropriate in a
  professional setting

### Our Responsibilities

Project maintainers are responsible for clarifying the standards of acceptable
behavior and are expected to take appropriate and fair corrective action in
response to any instances of unacceptable behavior.

Project maintainers have the right and responsibility to remove, edit, or
reject comments, commits, code, wiki edits, issues, and other contributions
that are not aligned to this Code of Conduct, or to ban temporarily or
permanently any contributor for other behaviors that they deem inappropriate,
threatening, offensive, or harmful.
