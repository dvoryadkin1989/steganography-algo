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
        byte[] info = new byte[infoLength];
        for (int i = META_INFO_LENGTH; i < infoLength; i++) {
            int x = i % container.getWidth();
            int y = i / container.getWidth();

            byte extractedByte = extractByte(container.getRGB(x, y));
            info[i] = extractedByte;
        }
        return info;
    }

    private int extractInfoLength(BufferedImage container) {
        byte[] lengthBytes = new byte[META_INFO_LENGTH];
        for (int i = 0; i < META_INFO_LENGTH; i++) {
            int x = i % container.getWidth();
            int y = i / container.getWidth();
            lengthBytes[i] = extractByte(container.getRGB(x, y));
        }
        return ByteBuffer.wrap(lengthBytes).getInt();
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
