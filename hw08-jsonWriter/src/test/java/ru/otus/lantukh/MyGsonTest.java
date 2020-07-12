package ru.otus.lantukh;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyGsonTest {
    private MyGson myGson;
    private Gson gson = new Gson();

    private static class TestObject {
        int num;
        String str;
        char chr;
        int[] intArr;
        String[] strArr;
        private List<Integer> lstInt;
        private List<String> lstStr;


        TestObject(int num, String str, char chr, int[] intArr, String[] strArr, List<Integer> lstInt, List<String> lstStr) {
            this.num = num;
            this.str = str;
            this.chr = chr;
            this.intArr = intArr;
            this.strArr = strArr;
            this.lstInt = lstInt;
            this.lstStr = lstStr;
        }
    }

    @BeforeEach
    public void setUp() {
        myGson = new MyGson();
    }

    @DisplayName("Корректно сериализует byte")
    @Test
    public void shouldSerializeByte() {
        assertThat(gson.toJson((byte) 1)).isEqualTo(myGson.toJson((byte) 1));
    }

    @DisplayName("Корректно сериализует short")
    @Test
    public void shouldSerializeShort() {
        assertThat(myGson.toJson((short) 1f)).isEqualTo(gson.toJson((short) 1f));
    }

    @DisplayName("Корректно сериализует int")
    @Test
    public void shouldSerializeInt() {
        assertThat(gson.toJson(1)).isEqualTo(myGson.toJson(1));
    }

    @DisplayName("Корректно сериализует long")
    @Test
    public void shouldSerializeLong() {
        assertThat(gson.toJson(1L)).isEqualTo(myGson.toJson(1L));
    }

    @DisplayName("Корректно сериализует null")
    @Test
    public void shouldSerializeNull() {
        assertThat(gson.toJson(null)).isEqualTo(myGson.toJson(null));
    }

    @DisplayName("Корректно сериализует char")
    @Test
    public void shouldSerializeChar() {
        assertThat(gson.toJson('b')).isEqualTo(myGson.toJson('b'));
    }

    @DisplayName("Корректно сериализует String")
    @Test
    public void shouldSerializeString() {
        assertThat(gson.toJson("bcd")).isEqualTo(myGson.toJson("bcd"));
    }

    @DisplayName("Корректно сериализует int[]")
    @Test
    public void shouldSerializeIntArray() {
        assertThat(gson.toJson(new int[]{1, 2, 3})).isEqualTo(myGson.toJson(new int[]{1, 2, 3}));
    }

    @DisplayName("Корректно сериализует short[]")
    @Test
    public void shouldSerializeShortArray() {
        assertThat(
                gson.toJson(new short[]{(short) 6, (short) 7, (short) 8}))
                .isEqualTo(myGson.toJson(new short[]{(short) 6, (short) 7, (short) 8}));
    }

    @DisplayName("Корректно сериализует List")
    @Test
    public void shouldSerializeList() {
        assertThat(gson.toJson(List.of(1, 2, 3))).isEqualTo(myGson.toJson(List.of(1, 2, 3)));
    }

    @DisplayName("Корректно сериализует Collections")
    @Test
    public void shouldSerializeCollections() {
        assertThat(gson.toJson(Collections.singletonList(1))).isEqualTo(myGson.toJson(Collections.singletonList(1)));
    }

    @DisplayName("Корректно сериализует произвольный объект")
    @Test
    public void shouldSerializeCustomObject() {
        assertThat(gson.toJson(new TestObject(
                2,
                "Test",
                'a',
                new int[]{1, 2, 3},
                new String[]{"aa", "bb", "cc"},
                List.of(1, 2, 3),
                Collections.singletonList("asd")))
        ).isEqualTo(myGson.toJson(new TestObject(
                2,
                "Test",
                'a',
                new int[]{1, 2, 3},
                new String[]{"aa", "bb", "cc"},
                List.of(1, 2, 3),
                Collections.singletonList("asd")))
        );
    }
}