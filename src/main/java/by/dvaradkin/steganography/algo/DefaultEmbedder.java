package by.dvaradkin.steganography.algo;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import by.dvaradkin.steganography.helper.SteganographyHelper;

@Component
public class DefaultEmbedder implements Embedder {

    private final SteganographyHelper helper;

    @Autowired
    public DefaultEmbedder(final SteganographyHelper helper) {
        this.helper = helper;
    }

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

            int rgbWithEmbeddedInfo = helper.embedByte(container.getRGB(x, y), infoWithMeta[i]);
            container.setRGB(x, y, rgbWithEmbeddedInfo);
        }
    }

}
