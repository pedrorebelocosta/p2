package org.nolhtaced.core.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageUtil {
    private static final String IMG_BASE_PATH = System.getenv("IMAGES_PATH");
    private static final File directory = new File(IMG_BASE_PATH);

    public static String saveImage(byte[] imageBytes) throws IOException {
        if (!directory.exists()) {
            Files.createDirectories(Paths.get(IMG_BASE_PATH));
        }

        String fileName = UUID.randomUUID() + ".png";
        String filePath = String.format("%s/%s", IMG_BASE_PATH, fileName);
        File image = new File(filePath);

        FileOutputStream fileOutputStream = new FileOutputStream(image);
        fileOutputStream.write(imageBytes);
        fileOutputStream.close();

        return fileName;
    }

    public static File getImageFile(String fileName) throws IOException {
        String filePath = String.format("%s/%s", IMG_BASE_PATH, fileName);
        return new File(filePath);
    }

    public static byte[] getImageBytes(String fileName) throws IOException {
        return Files.readAllBytes(getImageFile(fileName).toPath());
    }
}
