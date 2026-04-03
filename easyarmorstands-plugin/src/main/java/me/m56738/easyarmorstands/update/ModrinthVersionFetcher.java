package me.m56738.easyarmorstands.update;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import me.m56738.easyarmorstands.config.version.Version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

public class ModrinthVersionFetcher {
    private final Loader loader;
    private final String gameVersion;

    public ModrinthVersionFetcher(Loader loader, String gameVersion) {
        this.loader = loader;
        this.gameVersion = gameVersion;
    }

    public String fetchLatestVersion() throws IOException {
        JsonArray loaders = new JsonArray();
        loaders.add(new JsonPrimitive(loader.name().toLowerCase(Locale.ROOT)));

        JsonArray gameVersions = new JsonArray();
        gameVersions.add(new JsonPrimitive(gameVersion));

        URI uri = URI.create("https://api.modrinth.com/v2/project/easyarmorstands/version?loaders="
                + URLEncoder.encode(loaders.toString(), "UTF-8")
                + "&game_versions="
                + URLEncoder.encode(gameVersions.toString(), "UTF-8")
                + "&include_changelog=false");
        Version latestVersion = null;
        String latestVersionNumber = null;
        try (JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(uri.toURL().openStream(), StandardCharsets.UTF_8)))) {
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                String versionType = null;
                String status = null;
                String versionNumber = null;
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    switch (name) {
                        case "version_type":
                            versionType = reader.nextString();
                            break;
                        case "status":
                            status = reader.nextString();
                            break;
                        case "version_number":
                            versionNumber = reader.nextString();
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                if (Objects.equals(versionType, "release") && Objects.equals(status, "listed")) {
                    Version version = Version.tryParse(versionNumber);
                    if (version != null) {
                        if (latestVersion == null || latestVersion.compareTo(version) < 0) {
                            latestVersion = version;
                            latestVersionNumber = versionNumber;
                        }
                    }
                }
                reader.endObject();
            }
            reader.endArray();
        }
        return latestVersionNumber;
    }
}
