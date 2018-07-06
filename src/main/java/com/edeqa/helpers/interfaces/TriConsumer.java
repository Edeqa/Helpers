package com.edeqa.helpers.interfaces;

public interface TriConsumer<T,U,V> {
    void accept(T t, U u, V v);
}
