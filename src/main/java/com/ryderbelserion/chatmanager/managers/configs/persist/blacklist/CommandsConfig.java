package com.ryderbelserion.chatmanager.managers.configs.persist.blacklist;

import com.google.gson.annotations.Expose;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.vital.paper.api.files.json.Serializer;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CommandsConfig {

    @Expose
    public static List<String> banned_commands = new ArrayList<>() {{
        add("gamemode");
        add("plugins");
        add("gmc");
        add("op");
        add("pl");
    }};

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