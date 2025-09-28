val SampleHtml
    get(): String {
        val platform = getPlatform()
        return """
            <h1>Hello <strong>HTML Converter</strong> for Compose</h1>
            <p>This the first paragraph of the sample app running on <strong>${platform.name}</strong>!</p>
            <ul>
                <li><strong>Bold</strong></li>
                <li><em>Italic</em></li>
                <li><u>Underline</u></li>
                <li><del>Strikethrough</del></li>
                <li><code>Code</code></li>
                <li><a href="https://www.wikipedia.org/">Hyperlink with custom styling</a></li>
                <li><big>Bigger</big> and <small>smaller</small> text</li>
                <li><sup>Super</sup>text and <sub>sub</sub>text</li>
                <li>
                    <span style="color:#E40303;">Various</span>
                    <span style="color:darkorange;">text</span>
                    <span style="color:#FFED00; background-color:#0009;">foreground</span>
                    <span style="color:#008026;">and</span>
                    <span style="color:white; background:#004CFF;">background</span>
                    <span style="color:#732982;">colors</span>    
                </li>
                <li>A nested ordered list:
                    <ol>
                        <li>Item 1</li>
                        <li>Item 2</li>
                    </ol>
                </li>
                <li>A nested unordered list:
                    <ul>
                        <li>Item 1</li>
                        <li>Item 2</li>
                    </ul>
                </li>
            </ul>

            <dl>
                <dt>Term</dt>
                <dd>Description.</dd>
            </dl>

            A few HTML entities: &raquo; &copy; &laquo; &check;

            <blockquote>A blockquote, indented relatively to the main text.</blockquote>

            <pre>Preformatted text, preserving
            line breaks...    and spaces.</pre>

            <p>You reached the end of the document.<br />Thank you for reading!</p>
        """.trimIndent()
    }