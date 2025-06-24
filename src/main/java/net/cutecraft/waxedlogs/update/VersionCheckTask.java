package net.cutecraft.waxedlogs.update;

import lombok.AllArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

@AllArgsConstructor
public class VersionCheckTask extends BukkitRunnable {

    public static final String RESOURCE = "";

    private final Consumer<ComparableVersion> comparable;

    @Override
    public void run() {
        try {
            final URL url = new URI(RESOURCE).toURL();
            try (Scanner scanner = new Scanner(url.openStream())) {
                if (scanner.hasNext()) {
                    comparable.accept(new ComparableVersion(scanner.next()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to check version", e);
        }
    }
}