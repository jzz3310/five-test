package test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author:jzz
 * @date:2020/8/23
 */
public class TreeTest {

    public static void main(String[] args) {
        Tree<Integer, String> tree = new Tree<>();

        tree.add(1,"1");
        tree.add(2,"2");
        tree.add(3,"3");
        tree.add(4,"4");
        tree.add(5,"5");
        tree.add(6,"6");
        tree.add(7,"7");
        tree.add(8,"8");
        tree.add(9,"9");

//        Nodex nodex = tree.get(1);
//        System.out.println(nodex.getValue());

        ParameterizedType paramterizedType  = (ParameterizedType) tree.getClass().getGenericSuperclass();
    }

}
