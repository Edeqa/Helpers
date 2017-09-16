/**
 * Part of Waytous <http://waytous.net>
 * Copyright (C) Edeqa LLC <http://www.edeqa.com>
 *
 * Created 5/17/2017.
 */
package com.edeqa.helpers.interfaces;

public interface Runnable3<T,U,V> {
    void call(T arg1, U arg2, V arg3);
}
