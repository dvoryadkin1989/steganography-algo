package by.dvaradkin.steganography.algo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class Steganography implements Embedder, Extractor {

    private final Embedder embedder;
    private final Extractor extractor;

    @Autowired
    public Steganography(
            @Qualifier("defaultEmbedder") final Embedder embedder,
            @Qualifier("defaultExtractor") final Extractor extractor
    ) {
        this.embedder = embedder;
        this.extractor = extractor;
    }

    @Override
    public BufferedImage embed(final BufferedImage container, final byte[] info) {
        return embedder.embed(container, info);
    }

    @Override
    public byte[] extract(final BufferedImage container) {
        return extractor.extract(container);
    }
}
