package by.dvaradkin.steganography.meta;

import java.nio.ByteBuffer;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

@Component
public class MetaEnricher {

    public byte[] enrichWithMeta(final byte[] info, final Meta meta) {
        // TODO: enrich with info length
        final byte[] fileNameBytes = meta.getFileName().getBytes();
        byte[] lengthBytes = ByteBuffer.allocate(4).putInt(fileNameBytes.length).array();
        return ArrayUtils.addAll(ArrayUtils.addAll(lengthBytes, fileNameBytes), info);
    }

}
