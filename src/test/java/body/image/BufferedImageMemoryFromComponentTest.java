package body.image;

import body.core.logger.Loggerable;
import image.BufferedImageMemoryFromComponent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Created by vicboma on 10/02/17.
 */
public class BufferedImageMemoryFromComponentTest implements Loggerable{

    private BufferedImageMemoryFromComponent bufferedImageMemoryFromComponent;

    @Before
    public void setUp() throws Exception {
        bufferedImageMemoryFromComponent  = new BufferedImageMemoryFromComponent();
    }

    @After
    public void tearDown() throws Exception {
        bufferedImageMemoryFromComponent = null;
    }

    @Test
    public void testCreateFile() throws Exception {

        final JButton btn = new JButton();
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.YELLOW);

        JPanel p = new JPanel();
        p.add(btn);

        JPanel inner = new JPanel();
        inner.setBorder(BorderFactory.createTitledBorder("A border"));
        inner.add(new JLabel("Some label"));
        p.add(inner);

        BufferedImage img = bufferedImageMemoryFromComponent.invoke(p);

        final File file = new File("test.png");
        ImageIO.write(img, "png", file);

        Assert.assertTrue(file.exists());
    }

    @Test
    public void testButtonPixelImage() throws Exception {

        java.util.List<Integer> expectedYellow = Arrays.asList(6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 18, 19, 20);
        final int hpixelYellow = 13;

        final JButton btn = new JButton();
        btn.setBackground(Color.YELLOW);
        final JPanel p = new JPanel();
        p.add(btn);

        final BufferedImage img = bufferedImageMemoryFromComponent.invoke(p);

        for (int i = 0; i < expectedYellow.size(); i++) {
            int clr = img.getRGB(expectedYellow.get(i), hpixelYellow);
            Color color = new Color(clr, true);
            Assert.assertEquals(color, Color.YELLOW);
        }



    }
}
