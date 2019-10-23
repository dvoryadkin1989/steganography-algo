package by.dvaradkin.steganography.algo;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static by.dvaradkin.steganography.algo.Constants.THREE_BITS;
import static by.dvaradkin.steganography.algo.Constants.TWO_BITS;

@Component
public class DefaultEmbedder implements Embedder {

    @Override
    public BufferedImage embed(BufferedImage container, byte[] info) {
        byte[] infoWithMeta = addMeta(info);
        doEmbed(container, infoWithMeta);
        return container;
    }

    private byte[] addMeta(byte[] info) {
        byte[] lengthBytes = ByteBuffer.allocate(4).putInt(info.length).array();
        return ArrayUtils.addAll(lengthBytes, info);
    }

    private void doEmbed(BufferedImage container, byte[] infoWithMeta) {
        for (int i = 0; i < infoWithMeta.length; i++) {
            int x = i % container.getWidth();
            int y = i / container.getWidth();

            int rgbWithEmbeddedInfo = embedByte(container.getRGB(x, y), infoWithMeta[i]);
            container.setRGB(x, y, rgbWithEmbeddedInfo);
        }
    }

    private int embedByte(int containerRgb, byte infoByte) {
        final Color color = new Color(containerRgb);
        int result = (~THREE_BITS & color.getRed()) | (THREE_BITS & infoByte);
        result <<= 8;
        infoByte >>>= 3;
        result |= (~THREE_BITS & color.getGreen()) | (THREE_BITS & infoByte);
        result <<= 8;
        infoByte >>>= 3;
        result |= (~TWO_BITS & color.getBlue()) | (TWO_BITS & infoByte);
        result |= 0xFF_FF_FF_FF << 24;
        return result;
    }
}
