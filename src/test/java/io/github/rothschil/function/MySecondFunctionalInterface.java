package io.github.rothschil.function;

@FunctionalInterface
public interface MySecondFunctionalInterface<T, R> {

    R doSomething(T name);
}
