package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.vital.common.utils.FileUtil;
import org.bukkit.command.CommandSender;
import java.io.File;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class LogUtils {

    private static final ChatManager plugin = ChatManager.get();

    private static final File logsFolder = new File(plugin.getDataFolder(), "logs");

    public static void create() {
        if (!logsFolder.exists()) {
            logsFolder.mkdirs();
        }

        Files.advertisement_log_file.create();
        Files.command_log_file.create();
        Files.swear_log_file.create();
        Files.sign_log_file.create();
        Files.chat_log_file.create();
    }

    public static void write(final File file, final Date time, final CommandSender sender, final String format) {
        CompletableFuture.runAsync(() -> FileUtil.write(file, "[" + time + "] " + sender.getName() + format.replaceAll("§", "&")));
    }

    public static void zip() {
        CompletableFuture.runAsync(() -> FileUtil.zip(logsFolder, ".log", true));
    }
}