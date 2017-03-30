package fr.oiseau.parfume;

import com.google.common.base.Preconditions;

public class ArgumentPreconditions {

    public static <T> T notNull(T object) {
        return notNull(object, "Argument cannot be a null");
    }
    public static <T> T notNull(T object, String message) {
        Preconditions.checkArgument(object != null, message);
        return object;
    }
}
