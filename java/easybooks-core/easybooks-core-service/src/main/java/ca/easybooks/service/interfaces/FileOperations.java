package ca.easybooks.service.interfaces;

import java.io.File;

public interface FileOperations {

    void saveFile(String relativePath, byte[] fileContents);
    File getFile(String relativePath);
}
