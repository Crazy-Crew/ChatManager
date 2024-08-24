package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.vital.common.utils.FileUtil;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.command.CommandSender;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LogUtils {

    private static final ChatManager plugin = ChatManager.get();

    private static final ComponentLogger logger = plugin.getComponentLogger();

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
        try (final FileWriter writer = new FileWriter(file, true); final BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write("[" + time + "] " + sender.getName() + format.replaceAll("§", "&"));
            bufferedWriter.newLine();
            writer.flush();
        } catch (Exception exception) {
            logger.warn("Failed to write to: {}", file.getName(), exception);
        }
    }

    public static void zip() {
        final List<File> logFiles = FileUtil.getFileObjects(plugin.getDataFolder(), logsFolder.getName(), ".log");

        if (logFiles.isEmpty()) return;

        boolean hasNonEmptyFile = false;

        for (final File file : logFiles) {
            if (file.exists() && file.length() > 0) {
                hasNonEmptyFile = true;

                break;
            }
        }

        if (!hasNonEmptyFile) {
            if (plugin.isVerbose()) {
                logger.warn("All log files are empty. No zip file will be created.");
            }

            return;
        }

        final List<String> files = FileUtil.getFiles(logsFolder, ".gz", true);

        int count = files.size();

        count++;

        final String zipName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + count + ".gz";

        final File zip = new File(logsFolder, zipName);

        try (final FileOutputStream fileOutputStream = new FileOutputStream(zip); ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream)) {
            for (final File file : logFiles) {
                if (!file.exists()) continue;

                if (file.length() > 0) {
                    try (final FileInputStream fileInputStream = new FileInputStream(file)) {
                        final ZipEntry zipEntry = new ZipEntry(file.getName());

                        zipOut.putNextEntry(zipEntry);

                        byte[] bytes = new byte[1024];
                        int length;

                        while ((length = fileInputStream.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                    }

                    file.delete();
                } else {
                    if (plugin.isVerbose()) {
                        logger.warn("The file named {}'s size is 0, We are not adding to zip.", file.getName());
                    }
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}