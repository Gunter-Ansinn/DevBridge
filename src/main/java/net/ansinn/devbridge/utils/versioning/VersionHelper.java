package net.ansinn.devbridge.utils.versioning;

import java.util.Optional;
import java.util.jar.Manifest;

public class VersionHelper {

    public static Optional<Version> getVersion() {
        var pkg = VersionHelper.class.getPackage();

        if (pkg != null) {
            var title = getVersionTitle(pkg);
            var implVer = getImplVersion(pkg);
            var commitVer = getVersionCommit();


            return Optional.of(new Version(title, implVer, commitVer));
        }

        return Optional.empty();
    }

    private static String getVersionTitle(Package pkg) {
        if (pkg.getImplementationTitle() != null)
            return pkg.getImplementationTitle();
        else
            return "unknown";
    }

    private static String getImplVersion(Package pkg) {
        if (pkg.getImplementationVersion() != null)
            return pkg.getImplementationVersion();
        else
            return "dev";
    }

    private static String getVersionCommit() {
        var commit = "unknown";

        try (var inputStream = VersionHelper.class.getResourceAsStream("/META-INF/MANIFEST.MF")) {
            if (inputStream != null) {
                var manifest = new Manifest(inputStream);
                commit = String.valueOf(manifest.getMainAttributes().getValue("Implementation-Commit"));
            }
        } catch (Exception _) {}

        return commit;
    }

}
