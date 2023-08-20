package io.github.chopachopachopa.solanawallet.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.io.Resources;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class AbstractTest {
    protected static final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .addModule(new Jdk8Module())
            .addModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();

    protected <T> T readObject(
        final String child,
        final TypeReference<T> typeRef
    ) {
        T result;
        try {
            URL url = Resources.getResource(resourceLocation() + child);

            result = objectMapper.readValue(
                url,
                typeRef
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    protected <T> T readObject(
        final String child,
        final Class<T> clazz
    ) {
        return readObject(
            child,
            new TypeReference<>() {
                @Override
                public Type getType() {
                    return clazz;
                }
            }
        );
    }

    protected abstract String resourceLocation();
}
