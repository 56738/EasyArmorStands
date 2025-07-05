package me.m56738.easyarmorstands.update;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class SpigotVersionFetcher {
    private final String url;

    public SpigotVersionFetcher(String url) {
        this.url = url;
    }

    public String fetchLatestVersion() throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(new URL(url).openStream()))) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if ("current_version".equals(name)) {
                    return reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }
        return null;
    }
}
