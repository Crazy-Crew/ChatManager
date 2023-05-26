package com.ryderbelserion.chatmanager.api.enums;

public enum ConvertOptions {

    RENAME_FILES("rename_files"),
    CONVERT_OLD_FILES("convert_old_files");

    final String name;

    ConvertOptions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}