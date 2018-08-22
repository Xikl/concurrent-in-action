import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author 朱文赵
 * @date 2018/8/22 13:04
 * @description
 */
@Getter
@Setter
public class TestP {

    private String name;

    public TestP() {
    }
}

@Getter
@Setter
class Test2 extends TestP {

    private Integer idTest2;

    public Test2() {
    }
}

@Getter
@Setter
class Test3 extends TestP {

    private Integer idTest3;

    public Test3() {
    }
}

class Main {

    public static void main(String[] args) {
        Test2 test2 = new Test2();
        test2.setName("nihao");
        test2.setIdTest2(2);

        Test3 test3 = new Test3();
        BeanUtils.copyProperties(test2, test3);
        System.out.println(test3);

    }

}
