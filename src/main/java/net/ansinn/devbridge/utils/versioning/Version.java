package net.ansinn.devbridge.utils.versioning;

public record Version(String title, String version, String commit) {

    @Override
    public String toString() {
        return "Version{" +
                "title='" + title + '\'' +
                ", version='" + version + '\'' +
                ", commit='" + commit + '\'' +
                '}';
    }
}
