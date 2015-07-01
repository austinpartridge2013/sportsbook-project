package ca.easybooks.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileService implements AutoCloseable {
    private static final String DEFAULT_FILE_PATH = "/tmp/output/";

    private ZipOutputStream outputStream;
    private final File zipFile;

    public ZipFileService(final String zipFileName) {
        final Path filePath = Paths.get(DEFAULT_FILE_PATH, zipFileName);
        zipFile = filePath.toFile();
        try {
            Files.createDirectories(filePath.getParent());
            outputStream = new ZipOutputStream(new FileOutputStream(zipFile));
        } catch (final IOException e) {
            throw new RuntimeException("Error with zip file", e);
        }
    }

    public void addFile(final File file, final String filename) {
        try (final InputStream in = new FileInputStream(file)) {
            outputStream.putNextEntry(new ZipEntry(filename));

            final byte[] bytes = new byte[1024];
            int count;

            while ((count = in.read(bytes)) > 0) {
                System.out.println();
                outputStream.write(bytes, 0, count);
            }
        } catch (final IOException e) {
            throw new RuntimeException("Error adding file to zip archive", e);
        }
    }

    public File getZipFile() {
        return zipFile;
    }

    @Override
    public void close() {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (final IOException e) {
                // Swallow this exception
            }
        }

    }

}
