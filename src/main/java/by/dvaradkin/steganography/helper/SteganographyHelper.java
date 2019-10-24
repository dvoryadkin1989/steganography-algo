package by.dvaradkin.steganography.helper;

import static by.dvaradkin.steganography.algo.Constants.THREE_BITS;
import static by.dvaradkin.steganography.algo.Constants.TWO_BITS;

import java.awt.Color;
import org.springframework.stereotype.Component;

@Component
public class SteganographyHelper {

    public int embedByte(int containerRgb, byte infoByte) {
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

    public byte extractByte(int containerRgb) {
        final Color color = new Color(containerRgb);
        int result = (TWO_BITS & color.getBlue());

        result <<= 3;
        result |= (THREE_BITS & color.getGreen());

        result <<= 3;
        result |= (THREE_BITS & color.getRed());

        return (byte) (result & 0xFF);
    }
}
