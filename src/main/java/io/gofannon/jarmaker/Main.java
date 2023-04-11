package io.gofannon.jarmaker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.*;

public class Main {
    public static void main(String[] args) throws IOException {
        generateCopyJar();
    }

    private static void generateCopyJar() throws IOException {
        File jarFile = getJarFile();

        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(new Attributes.Name("Automatic-Module-Name"), "my.sample.component");


        try (FileOutputStream fileOut = new FileOutputStream(jarFile);
             JarOutputStream jarOut = new JarOutputStream(fileOut, manifest)) {

            try (
                    FileInputStream fileIn = new FileInputStream("./data/jdt.jar");
                    JarInputStream jarIn = new JarInputStream(fileIn)) {
                for (JarEntry entry = jarIn.getNextJarEntry(); entry != null; entry = jarIn.getNextJarEntry()) {
                    if (entry.getName().startsWith("META-INF/MANIFEST.MF"))
                        continue;
                    if (entry.isDirectory()) {
                        createDirectory(jarOut, entry);
                    } else {
                        duplicateEntry(jarOut, jarIn, entry);
                    }
                }
            }

        }
    }

    private static void createDirectory(JarOutputStream jarOut, JarEntry entry) throws IOException {
        JarEntry newEntry = new JarEntry(entry);
        jarOut.putNextEntry(newEntry);
        jarOut.closeEntry();
    }

    private static void duplicateEntry(@NotNull JarOutputStream jarOut, @NotNull JarInputStream jarIn, @NotNull JarEntry entry) throws IOException {
        JarEntry newEntry = new JarEntry(entry);
        jarOut.putNextEntry(newEntry);
        int bufferLength = 0;
        byte[] buffer = new byte[500000];
        while (bufferLength >= 0) {
            bufferLength = jarIn.read(buffer, 0, buffer.length);
            if (bufferLength > 0) {
                jarOut.write(buffer, 0, bufferLength);
            }
        }
        jarOut.closeEntry();
    }


    private static @NotNull File getJarFile() {
        File jarFile = new File("./data/my-sample-component-1.0.jar");
        FileUtils.deleteQuietly(jarFile);
        return jarFile;
    }

    private void generateThinJar() throws IOException {
        File jarFile = getJarFile();
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(new Attributes.Name("Automatic-Module-Name"), "my.sample.component");


        try (FileOutputStream fileOut = new FileOutputStream(jarFile);
             JarOutputStream jarOut = new JarOutputStream(fileOut, manifest)) {
            JarEntry jarEntry = new JarEntry("sample/sample.txt");
            jarOut.putNextEntry(jarEntry);
            jarOut.write(
                    IOUtils.resourceToByteArray("/sample/sample.txt")
            );
            jarOut.closeEntry();
        }
    }
}