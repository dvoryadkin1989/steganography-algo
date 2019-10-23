package by.dvaradkin.steganography.algo;

import java.awt.image.BufferedImage;

public interface Extractor {

    byte[] extract(BufferedImage container);

}
