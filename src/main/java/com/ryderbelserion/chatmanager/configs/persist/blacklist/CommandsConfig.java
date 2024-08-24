package com.ryderbelserion.chatmanager.configs.persist.blacklist;

import com.google.gson.annotations.Expose;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.vital.paper.api.files.json.Serializer;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CommandsConfig {

    @Expose
    public static List<String> banned_commands = new ArrayList<>();

    static {
        banned_commands.add("gamemode");
        banned_commands.add("plugins");
        banned_commands.add("gmc");
        banned_commands.add("op");
        banned_commands.add("pl");
    }

    private final Serializer<CommandsConfig> serializer;

    public CommandsConfig() {
        this.serializer = new Serializer<>(Files.commands_file.getFile(), this).withoutExposeAnnotation().withoutModifiers(Modifier.TRANSIENT).setPrettyPrinting();
    }

    public void save() {
        this.serializer.write();
    }

    public void load() {
        this.serializer.load();
    }
}