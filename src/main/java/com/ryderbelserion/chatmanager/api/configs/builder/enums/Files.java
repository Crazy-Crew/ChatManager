package com.ryderbelserion.chatmanager.api.configs.builder.enums;

import java.io.File;

public enum Files {

    config("placeholder.yml", new File("placeholder.yml"));

    private final String name;
    private final File file;

    Files(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return this.name;
    }

    public File getFile() {
        return this.file;
    }
}