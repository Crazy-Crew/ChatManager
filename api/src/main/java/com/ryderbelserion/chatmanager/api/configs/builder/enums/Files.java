package com.ryderbelserion.chatmanager.api.configs.builder.enums;

import java.io.File;

public enum Files {

    advertisement_logs("advertisements.txt", new File("advertisements.txt")),
    chat_logs("chat.txt", new File("chat.txt")),
    command_logs("commands.txt", new File("commands.txt")),
    swear_logs("swears.txt", new File("swears.txt")),
    sign_logs("signs.txt", new File("signs.txt"));

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