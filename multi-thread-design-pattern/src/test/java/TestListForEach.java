import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * @author 朱文赵
 * @date 2018/8/17 16:41
 * @description
 */
public class TestListForEach {

    @Test
    public void test() {
        Optional<List<String>> list = Optional.empty();
        list.ifPresent(strings -> {
            strings.forEach(System.out::println);
        });

    }

    @Test
    public void testString() {
        String a = "1234";
        String replace = replace(a);

    }

    private String replace(String str) {
        String ab = str.replace("12", "ab");
        return ab;
    }

    @Test
    public void testName() {
        A a = new A("1234566");
        String name = a.getName();
        name = StringUtils.replace(name, "123", "abc");
    }



}

@Data
@NoArgsConstructor
@AllArgsConstructor
class A {
    private String name;

}