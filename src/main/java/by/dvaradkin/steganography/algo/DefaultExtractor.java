package by.dvaradkin.steganography.algo;

import org.springframework.stereotype.Component;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static by.dvaradkin.steganography.algo.Constants.THREE_BITS;
import static by.dvaradkin.steganography.algo.Constants.TWO_BITS;

@Component
public class DefaultExtractor implements Extractor {

    private static final int META_INFO_LENGTH = 4;

    @Override
    public byte[] extract(BufferedImage container) {
        int infoLength = extractInfoLength(container);
        return extractBytes(container, META_INFO_LENGTH, infoLength);
    }

    private int extractInfoLength(BufferedImage container) {
        byte[] infoLengthBytes = extractBytes(container, 0, META_INFO_LENGTH);
        return ByteBuffer.wrap(infoLengthBytes).getInt();
    }

    private byte[] extractBytes(BufferedImage container, int startIndex, int length) {
        byte[] info = new byte[length];
        for (int i = startIndex; i < length; i++) {
            int x = i % container.getWidth();
            int y = i / container.getWidth();

            info[i] = extractByte(container.getRGB(x, y));
        }
        return info;
    }

    private byte extractByte(int containerRgb) {
        final Color color = new Color(containerRgb);
        int result = (TWO_BITS & color.getBlue());

        result <<= 3;
        result |= (THREE_BITS & color.getGreen());

        result <<= 3;
        result |= (THREE_BITS & color.getRed());

        return (byte) (result & 0xFF);
    }
}
