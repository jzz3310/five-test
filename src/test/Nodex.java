package test;

import java.io.File;

/**
 * @author:jzz
 * @date:2020/8/23
 */
public class Nodex<T,X> {
    private T key;
    private X value;
    private Nodex next;
    private Nodex pre;

    public T getKey() {
        return key;
    }

    public Nodex(T key, X value) {
        this.key = key;
        this.value = value;
    }

    public X getValue () {
        return value;
    }

    public Nodex next() {
        return next;
    }

    public Nodex pre() {
        return pre;
    }

    public void add (Nodex node) {
        if(key.hashCode() == node.getKey().hashCode()){
            value = (X) node.getValue();
        } else {
            if(compare(node)) {
                if(null == next) {
                    next = node;
                } else {
                    next.add(node);
                }
            } else {
                if(null == pre) {
                    pre = node;
                } else {
                    pre.add(node);
                }
            }
        }
    }

    private boolean compare (Nodex node) {
        Object key1 = node.getKey();
        int i = key.hashCode();
        int i1 = key1.hashCode();
        return i1 >= i;
    }

    public Nodex get(T key1) {
        System.out.println("一次");
        if(key.hashCode() == key1.hashCode()){
            return this;
        }
        try {
            if((key1.hashCode()-key.hashCode()) >= 0) {
                return next.get(key1);
            } else {
                return pre.get(key1);
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
}
