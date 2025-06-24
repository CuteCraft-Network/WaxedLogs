package net.cutecraft.waxedlogs.update;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ComparableVersion implements Comparable<ComparableVersion> {

    @Getter
    private final String rawVersion;
    private final List<Integer> parts;

    public ComparableVersion(String rawVersion) {
        this.rawVersion = rawVersion;
        this.parts = parseParts(rawVersion);
    }

    private List<Integer> parseParts(String version) {
        final String[] split = version.split("\\.");
        final List<Integer> result = new ArrayList<>();
        for (String s : split) {
            try {
                result.add(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                result.add(0);
            }
        }
        return result;
    }

    @Override
    public int compareTo(ComparableVersion other) {
        int maxLength = Math.max(this.parts.size(), other.parts.size());
        for (int i = 0; i < maxLength; i++) {
            int thisPart = i < this.parts.size() ? this.parts.get(i) : 0;
            int otherPart = i < other.parts.size() ? other.parts.get(i) : 0;
            if (thisPart != otherPart) {
                return thisPart - otherPart;
            }
        }
        return 0;
    }
}