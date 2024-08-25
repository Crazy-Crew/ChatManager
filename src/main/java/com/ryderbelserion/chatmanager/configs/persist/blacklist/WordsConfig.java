package com.ryderbelserion.chatmanager.configs.persist.blacklist;

import com.google.gson.annotations.Expose;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.vital.paper.api.files.json.Serializer;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class WordsConfig {

    @Expose
    public static List<String> banned_words = new ArrayList<>() {{
        add("anal");
    }};

    @Expose
    public static List<String> allowed_words = new ArrayList<>() {{
        add("an alt");
    }};

    private final Serializer<WordsConfig> serializer;

    public WordsConfig() {
        this.serializer = new Serializer<>(Files.words_file.getFile(), this).withoutExposeAnnotation().withoutModifiers(Modifier.TRANSIENT).setPrettyPrinting();
    }

    public void save() {
        this.serializer.write();
    }

    public void load() {
        this.serializer.load();
    }
}