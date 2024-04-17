package com.sithub.sithub.config;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StreamUtils;
import org.springframework.util.FileSystemUtils;

import java.io.*;

public class StringToMultipartFileConverter {

    public static MultipartFile convert(String content, String filename, String contentType) throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(content.getBytes());
        fos.close();

        return new TemporaryMultipartFile(tempFile, filename, contentType);
    }

    private static class TemporaryMultipartFile implements MultipartFile {
        private final File file;
        private final String name;
        private final String contentType;

        public TemporaryMultipartFile(File file, String name, String contentType) {
            this.file = file;
            this.name = name;
            this.contentType = contentType;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getOriginalFilename() {
            return this.name;
        }

        @Override
        public String getContentType() {
            // You can set the content type based on your needs
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return this.file.length();
        }

        @Override
        public byte[] getBytes() throws IOException {
            return StreamUtils.copyToByteArray(this.getInputStream());
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new FileInputStream(this.file);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            FileSystemUtils.copyRecursively(this.file, dest);
        }
    }
}

