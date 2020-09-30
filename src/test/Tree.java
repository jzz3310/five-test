package test;

/**
 * @author:jzz
 * @date:2020/8/23
 */
public class Tree<T,X> {
    private Nodex<T,X> nodex;


    public boolean add(T key, X value) {
        if(null == key) {
            throw new NullPointerException();
        }
        if(null == nodex){
            nodex = new Nodex<>(key,value);
        } else {
            nodex.add(new Nodex(key,value));
        }
        return true;
    }

    public Nodex get(T key1) {
        Nodex node = this.nodex.get(key1);
        return node;
    }
}
