package com.cullenye.concurrent.cha10.lambda;

/**
 * Lambda表达式的隐性final语义
 * @author yeguanhong
 */
public class LambdaFinalTester {

    public interface Converter<T1, T2> {
        void convert(int i);
    }

    public static void main(String[] args) {
        int num = 5;
        Converter<Integer, String> s = (param) -> System.out.println(String.valueOf(param + num));
        s.convert(2);
        // 报错信息：Local variable num defined in an enclosing scope must be final or effectively
        // num = 5;
    }
}
