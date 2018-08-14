import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author 朱文赵
 * @date 2018/8/14 16:18
 * @description
 */
public class TestClass {


    @Test
    public void test() {
        int i = get(1);
        System.out.println(i);
    }

    private int get(int a) {
        switch (a) {
            case 1:
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    public static <T, R> R convert(T t, Class<R> clazz) {
        try {
            R instance = clazz.newInstance();
            BeanUtils.copyProperties(t, instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testConvert() {
        B b = new B();
        b.setId(1);
        b.setName("sss");
        C c = convert(b, C.class);
        System.out.println(c.getId() + "" + c.getName());
    }



}

@Getter
@Setter
class A {
    private Integer id;

}

@Getter
@Setter
class B extends A{
    private String name;
}

@Getter
@Setter
class C {
    private Integer id;
    private String name;
}

