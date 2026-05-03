package me.m56738.easyarmorstands.message;

import com.google.gson.stream.JsonWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MessageMigrator {
    private static final Pattern PATTERN = Pattern.compile("messages\\.properties|messages_(.+)\\.properties");
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{(\\d+)}");

    private MessageMigrator() {
    }

    public static boolean migrate(Path path, Path targetFolder) throws IOException {
        Matcher matcher = PATTERN.matcher(path.getFileName().toString());
        if (!matcher.matches()) {
            return false;
        }

        String languageName = matcher.group(1);
        if (languageName == null) {
            languageName = "en_us";
        }
        if (languageName.equals("de")) {
            languageName = "de_de";
        }

        Files.createDirectories(targetFolder);

        Path targetFile = targetFolder.resolve(languageName + ".json");
        if (Files.exists(targetFile)) {
            return false;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            try (JsonWriter writer = new JsonWriter(Files.newBufferedWriter(targetFile, StandardCharsets.UTF_8))) {
                writer.setIndent("  ");
                writer.beginObject();
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        writer.name(parts[0]);
                        writer.value(convertValue(parts[1]));
                    }
                }
                writer.endObject();
            }
        }
        Files.delete(path);
        return true;
    }

    private static String convertValue(String value) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(value);
        StringBuilder result = new StringBuilder();
        int counter = 1;
        int lastEnd = 0;
        while (matcher.find()) {
            result.append(value, lastEnd, matcher.start());
            int index = Integer.parseInt(matcher.group(1)) + 1;
            if (index == counter) {
                result.append("%s");
                counter++;
            } else {
                result.append("%").append(index).append("$s");
            }
            lastEnd = matcher.end();
        }
        result.append(value, lastEnd, value.length());
        return result.toString();
    }
}
