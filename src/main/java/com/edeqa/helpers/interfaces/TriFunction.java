package com.edeqa.helpers.interfaces;

public interface TriFunction<T, U, V, R> {
    R call(T t, U u, V v);
}
