package com.jw.core.entity;

public class Attachment {

    private final String fileName;

    private final byte[] data;

    private final String contentType;

    public Attachment(String fileName, byte[] data, String contentType) {
        this.fileName = fileName;
        this.data = data;
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }

}
