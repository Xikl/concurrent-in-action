package com.ximo.thinkingandprogramming.stage3.list;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xikl
 * @date 2019/6/5
 */
public class ListRemoveTest {

    public static void main(String[] args) {
        List<String> stringList = Stream.of("a", "b", "b", "b", "c").collect(Collectors.toList());

        // 报错
        for (String s : stringList) {
            if ("b".equals(s)) {
                stringList.remove(s);
            }
        }

        // 数据往前移动 临近的重复元素则无法删除
        // [a, b, c]
        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).equals("b")) {
                stringList.remove(i);
            }
        }

        System.out.println(stringList);


    }

}
