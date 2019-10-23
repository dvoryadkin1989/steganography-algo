package by.dvaradkin.steganography.algo;

import java.awt.image.BufferedImage;

public interface Embedder {

    BufferedImage embed(BufferedImage container, byte[] info);

}
