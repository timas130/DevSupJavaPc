package com.sup.dev.java_pc.tools

import java.awt.Toolkit


object ToolsPcScreen {


    val screenWidth: Double
        get() = Toolkit.getDefaultToolkit().screenSize.getWidth()

    val screenHeight: Double
        get() = Toolkit.getDefaultToolkit().screenSize.getHeight()

    val screenDPI: Double
        get() = 240.0

    val screenDPRation: Double
        get() = screenDPI / 160

    val screenSPRation: Double
        get() = screenDPI / 160

}
