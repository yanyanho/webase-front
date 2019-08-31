package com.miya.token.file;
import lombok.Data;

import java.io.InputStream;

@Data
public class FileContent {
    private String  fileName;
    private InputStream inputStream;

    public FileContent(String fileName, InputStream inputStream) {
        this.fileName = fileName;
        this.inputStream = inputStream;
    }

}

