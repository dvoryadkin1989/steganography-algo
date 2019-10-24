package by.dvaradkin.steganography.algo;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import by.dvaradkin.steganography.helper.SteganographyHelper;

@Component
public class DefaultExtractor implements Extractor {

    private static final int META_INFO_LENGTH = 4;

    private final SteganographyHelper helper;

    @Autowired
    public DefaultExtractor(final SteganographyHelper helper) {
        this.helper = helper;
    }

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
        for (int i = startIndex; i < startIndex + length; i++) {
            int x = i % container.getWidth();
            int y = i / container.getWidth();

            info[i - startIndex] = helper.extractByte(container.getRGB(x, y));
        }
        return info;
    }

}
