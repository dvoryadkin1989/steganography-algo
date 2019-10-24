package by.dvaradkin.steganography;

import by.dvaradkin.steganography.algo.Steganography;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final String EMPTY_CONTAINER_FILE_NAME = "123.bmp";
    private static final String FILLED_CONTAINER_FILE_NAME = "output.bmp";
    private static final String FILE_TO_HIDE_NAME = "info_linux.txt";
    private static final String OUTPUT_FILE_NAME = "output.txt";
    private static final String IMAGE_FORMAT = "bmp";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final Steganography steganography;

    @Autowired
    public Application(final Steganography steganography) {
        this.steganography = steganography;
    }

    @Override
    public void run(String... args) throws Exception {
        final BufferedImage imageBmp = loadContainerImage();
        final BufferedImage container = steganography.embed(imageBmp, infoAsByteArray());

        byte[] extracted = steganography.extract(container);
        try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE_NAME)) {
            fos.write(extracted);
        }
        try (FileOutputStream fos = new FileOutputStream(FILLED_CONTAINER_FILE_NAME)) {
            ImageIO.write(container, IMAGE_FORMAT, fos);
        }
    }

    private BufferedImage loadContainerImage() throws IOException {
        return ImageIO.read(new ClassPathResource(EMPTY_CONTAINER_FILE_NAME).getFile());
    }

    private byte[] infoAsByteArray() throws IOException {
        return IOUtils.toByteArray(new ClassPathResource(FILE_TO_HIDE_NAME).getInputStream());
    }

}
