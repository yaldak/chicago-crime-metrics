package util;

import com.google.gson.JsonElement;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Tools to make JSON traversal less painful
 *
 * @author ykako
 *
 */
public final class GsonUtil {
    private GsonUtil() { }

    public static Optional<JsonElement> seekElementFromRoot(final JsonElement root,
            final String pathFromRoot, final Consumer<Exception> onError) {
        Objects.requireNonNull(pathFromRoot);
        Objects.requireNonNull(onError);

        String[] pathNodes = pathFromRoot.split("\\.");
        if (pathNodes.length == 0) {
            return Optional.ofNullable(root);
        }

        JsonElement cursor = root;

        for (int i = 0; i < pathNodes.length; i++) {
            if (Objects.isNull(cursor) || !cursor.isJsonObject()) {
                return Optional.empty();
            }

            cursor = cursor.getAsJsonObject().get(pathNodes[i]);
        }

        return Optional.ofNullable(cursor);
    }
}
