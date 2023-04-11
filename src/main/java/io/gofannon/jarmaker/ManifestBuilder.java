package io.gofannon.jarmaker;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static io.gofannon.jarmaker.ManifestHelper.isValidAttributeName;

public class ManifestBuilder {

    private final Map<Attributes.Name,String> attributes = new HashMap<>();

    public ManifestBuilder() {
        attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
    }

    public ManifestBuilder addAttribute(@NotNull String name, @NotNull String value) {
        assertValidAttributeName(name);
        Attributes.Name attName = new Attributes.Name(name);
        attributes.put(attName, value);
        return this;
    }

    private void assertValidAttributeName(String name) {
        if( !isValidAttributeName(name))
            throw new InvalidAttributeNameException(name,"'"+name+"' is not a valid attribute name");
    }

    public ManifestBuilder removeAttribute(@NotNull String name) {
        Attributes.Name attName = new Attributes.Name(name);
        attributes.remove(attName);
        return this;
    }

    @NotNull
    public Manifest build() {
        Manifest manifest = new Manifest();
        attributes.forEach((k,v) -> manifest.getMainAttributes().put(k,v));

        return manifest;
    }
}
