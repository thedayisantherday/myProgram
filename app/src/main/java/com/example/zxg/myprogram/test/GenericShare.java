package com.example.zxg.myprogram.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * description ：
 * Created by zhuxiaoguang at 00:00 on 2019/8/20
 */
public class GenericShare {

    private static final String TAG = GenericShare.class.getSimpleName();

    public static void main(String[] args) throws Exception {

        //JDK1.5之前和之后通用类的区别
        diff();

        // 擦除
        erase();

        // <？extends Fruit >和<？super Fruit >的区别
        diffExtendsAndSuper();

        // 自限定类型
        selfBounded();

        // 泛型方法
        genericMethod();

        // 生成器
        generator();

       /* IntegerGenerator integerGenerator = new IntegerGenerator();
        for (int i = 0; i < ; i++) {
            integerGenerator.next();
        }*/

        //自动装箱、自动拆箱
        autoUnpackAndPack();

        capture();

    }

    /**
     * JDK1.5之前和之后通用类的区别
     */
    public static void diff() {
        Holder holder = new Holder("123");
        String testStr0 = (String)holder.get();
//        int testInt0 = (int)holder.get();

        GenericHolder<Holder> holder1 = new GenericHolder<>(new Holder(1));
        Holder testStr = holder1.get();
        List<String> testList = new ArrayList<>();
        testList.add("123");
        String testStr1 = testList.get(0);
//        holder1.set(12); // error
    }

    public static class Holder {
        public Object entity;
        public Holder(Object t) {
            entity = t;
        }

        public Object get() {
            return entity;
        }

        public void set(Object entity) {
            this.entity = entity;
        }
    }

    public static class GenericHolder<T> {
        public T entity;
        public GenericHolder() {}

        public GenericHolder(T t) {
            entity = t;
        }

        public T get() {
            return entity;
        }

        public void set(T entity) {
            this.entity = entity;
        }

        /*public void doSomething() {
            entity.getValue();
        }*/

//        private T[] entitys = new T[10];
    }

    public static class GenericHolder2<T extends Entity> {
        public T entity;

        public GenericHolder2() {}

        public GenericHolder2(T t) {
            entity = t;
        }

        public T get() {
            return entity;
        }

        public void set(T entity) {
            this.entity = entity;
        }

        public void doSomething() {
            entity.getValue();
        }
    }

    public static class SuperEntity {
        private int value;
        public SuperEntity(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static class Entity extends SuperEntity{
        public Entity(int value) {
            super(value);
        }
    }

    public static class SubEntity extends Entity {
        public SubEntity(int value) {
            super(value);
        }
    }

    /**
     * 擦除
     */
    public static void erase() {

        Class class1 = new ArrayList<String>().getClass();
        Class class2 = new ArrayList<Integer>().getClass();
        System.out.println(TAG + "擦除 class1 == class2 " + (class1 == class2));

        System.out.println(TAG + "擦除 " + new GenericHolder<String>().getClass().getTypeParameters());
        System.out.println(TAG + "擦除 " + class1.getTypeParameters());

//        new GenericHolder<Entity>(new Entity(1)).doSomething();
        new GenericHolder2(new Entity(1)).doSomething();

        new GenericHolder<Entity>(new Entity(1)).get().getValue();

        GenericHolder2<? extends Entity> genericHolder1 = new GenericHolder2();
//        genericHolder1.set(new Entity(1));
//        SubEntity entity10 = genericHolder1.get();
        Entity entity11 = genericHolder1.get();
        SuperEntity entity12 = genericHolder1.get();

        GenericHolder2<? super Entity> genericHolder2 = new GenericHolder2();
//        genericHolder2.set(new SuperEntity(1));
        genericHolder2.set(new Entity(1));
//        genericHolder2.set(new SubEntity(1));
        Entity entity14 = genericHolder2.get();

        List<? extends Entity> list1 = new ArrayList<>();
//        list1.add(new Entity(1));
//        Entity entity21 = list1.get(0);
    }

    /**
     * <？extends Fruit >和<？super Fruit >的区别
     */
    public static void diffExtendsAndSuper() {
        GenericHolder<? extends Entity> holder3 = new GenericHolder<>();
//        holder3.set(new Entity(1));
        Entity entity3 = holder3.get();
        GenericHolder2<? extends Entity> holder4 = new GenericHolder2<>();
//        holder4.set(new Entity(1));
        Entity entity4 = holder4.get();
        GenericHolder2<? super Entity> holder5 = new GenericHolder2<>();
        holder5.set(new Entity(1));
        Entity entity5 = holder5.get();

        List<Entity> list1 = new ArrayList<>();
        list1.add(new Entity(1));

        List<? extends Entity> list2 = new ArrayList<>();
//        list2.add(new Entity(1));
        list2 = list1;
        Entity entity1 = list2.get(0);


        List<? super Entity> list3 = new ArrayList<>();
        list3.add(new Entity(1));
//        Entity entity2 = list3.get(0);
        Object object = list3.get(0);
        Entity entity2 = (Entity) list3.get(0);
    }


    /**
     * 自限定类型
     */
    public static void selfBounded() {
        Sub sub = new Sub();
        sub.set(new Sub());		//Ok
        sub.set(new Basic());	//调用了继承自Basic的set函数

        Subtype subtype = new Subtype();
//        subtype.set(new SelfBounded()); // error
        subtype.set(new Subtype());
    }

    public static class Basic{
        Basic b;
        public void set(Basic b){ this.b = b; }
    }

    public static class Sub extends Basic{
        Sub b;
        public void set(Sub b){ this.b = b; }
    }


    public static class SelfBounded< T extends SelfBounded<T>>  {
        private T entity;
        public void set(T e) {
            this.entity = e;
        }
    }

    public static class Subtype extends SelfBounded<Subtype> {

    }

    /**
     * 泛型方法
     */
    public static void genericMethod() {
        GenericMethod genericMethod = new GenericMethod();
        genericMethod.method("123");
        genericMethod.method(123);
        genericMethod.method(123.0);
    }

    /**
     * 泛型方法
     */
    public static class GenericMethod {
        public <T> void method(T t) {
            System.out.println("GenericMethod method: " + t.getClass().getName());
        }
    }

    /**
     * 生成器
     */
    public static void generator() {
        IntegerGenerator integerGenerator = new IntegerGenerator();
        integerGenerator.next();

        Generator<Holder> basicGenerator = BasicGenerator.create(Holder.class);
        basicGenerator.next();
    }

    public interface Generator<T> {
        T next();
    }

    public static class IntegerGenerator implements Generator<java.lang.Integer>{

        private static Random random = new Random();
        private int mod = 100;

        @Override
        public java.lang.Integer next(){
            return random.nextInt(mod);
        }
    }

    public static class BasicGenerator<T> implements Generator<T>{

        private Class<T> type;

        public BasicGenerator(Class<T> type) {
            this.type = type;
        }

        @Override
        public T next(){
            try {
                return type.newInstance();
            } catch (Exception e) {

            } finally {
                return null;
            }
        }

        public static <T> Generator<T> create(Class<T> type) {
            return new BasicGenerator<>(type);
        }
    }

    public static void autoUnpackAndPack() {
//        GenericHolder<int> holder2 = new GenericHolder<>(12); // error
        GenericHolder<Integer> holder2 = new GenericHolder<>();
        Integer integer = new Integer(12);
        holder2.set(integer);

        holder2.set(12);
        int testInt =  holder2.get();
    }

    public static void capture() {
        CaptureHolder captureHolder = new CaptureHolder();
        GenericHolder<Integer> genericHolder = new GenericHolder<>();
        captureHolder.doSomething1(genericHolder);
        captureHolder.doSomething2(genericHolder);
    }

    public static class CaptureHolder {
        public <T> void doSomething1(GenericHolder<T> genericHolder) {
            T t = genericHolder.get();
        }

        public void doSomething2(GenericHolder<?> genericHolder) {
            doSomething1(genericHolder);
        }
    }
}
