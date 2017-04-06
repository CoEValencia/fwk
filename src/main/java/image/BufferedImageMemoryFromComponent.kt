package image

import java.awt.Component
import java.awt.Container
import javax.swing.CellRendererPane
import java.awt.image.BufferedImage


/**
 * Created by vbolinch on 06/04/2017.
 */
class BufferedImageMemoryFromComponent internal constructor() {

    fun invoke(cmp: Component): BufferedImage {

        cmp.setSize(cmp.getPreferredSize())
        layoutComponent(cmp)
        val img = BufferedImage(cmp.getWidth(), cmp.getHeight(), BufferedImage.TYPE_INT_RGB)
        val crp = CellRendererPane()
        crp.add(cmp)
        crp.paintComponent(img.createGraphics(), cmp, crp, cmp.getBounds())

        return img
    }

    private fun layoutComponent(c: Component) {
        synchronized(c.getTreeLock()) {
            c.doLayout()
            if (c is Container)
                for (child in c.getComponents())
                    layoutComponent(child)
        }
    }
}