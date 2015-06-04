package com.thefightpredictor.sportsbook.core.data.transferobject;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class LedgerEntryInput {
    private String fileName;
    private byte[] fileData;

    public String getFileName() {
        return fileName;
    }

    @FormParam("fileName")
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    @FormParam("selectedFile")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public void setFileData(final byte[] fileData) {
        this.fileData = fileData;
    }
}
