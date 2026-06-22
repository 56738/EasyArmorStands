package me.m56738.easyarmorstands.update;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import org.apache.maven.artifact.versioning.ComparableVersion;

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

    public ComparableVersion fetchLatestVersion() throws IOException {
        JsonArray loaders = new JsonArray();
        loaders.add(new JsonPrimitive(loader.name().toLowerCase(Locale.ROOT)));

        JsonArray gameVersions = new JsonArray();
        gameVersions.add(new JsonPrimitive(gameVersion));

        URI uri = URI.create("https://api.modrinth.com/v2/project/easyarmorstands/version?loaders="
                + URLEncoder.encode(loaders.toString(), StandardCharsets.UTF_8)
                + "&game_versions="
                + URLEncoder.encode(gameVersions.toString(), StandardCharsets.UTF_8)
                + "&include_changelog=false");
        ComparableVersion latestVersion = null;
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
                    if (versionNumber != null) {
                        ComparableVersion version = new ComparableVersion(versionNumber);
                        if (latestVersion == null || latestVersion.compareTo(version) < 0) {
                            latestVersion = version;
                        }
                    }
                }
                reader.endObject();
            }
            reader.endArray();
        }
        return latestVersion;
    }
}
