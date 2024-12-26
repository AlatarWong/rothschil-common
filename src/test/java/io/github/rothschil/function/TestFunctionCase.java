package io.github.rothschil.function;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;

public class TestFunctionCase {

    @Test
    public void testSecondFunction() {
        MySecondFunctionalInterface<String, Integer> inputLength = String::length;
        MySecondFunctionalInterface<String, String> addGreeting = s -> "welcome " + s;
        MySecondFunctionalInterface<String, String> revertInput = StrUtil::reverse;


        System.out.println(inputLength.doSomething("hello world"));
        System.out.println(addGreeting.doSomething("sea"));
        System.out.println(revertInput.doSomething("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));

        // 输出信息
        // 11
        // welcome sea
        // ZYXWVUTSRQPONMLKJIHGFEDCBA
    }
}
