package io.gofannon.jarmaker;

import java.util.regex.Pattern;

public final  class ManifestHelper {

    private static final Pattern VALID_ATTRIBUTE_NAME = Pattern.compile("[a-zA-Z0-9]{1,70}?");

    private ManifestHelper() {
        throw new UnsupportedOperationException("Static class cannot be instanced");
    }

    public static boolean isValidAttributeName(String name) {
        return name!=null && VALID_ATTRIBUTE_NAME.matcher(name).matches();
    }

}
