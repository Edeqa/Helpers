/**
 * Part of Waytous <http://waytous.net>
 * Copyright (C) Edeqa LLC <http://www.edeqa.com>
 *
 * Created 5/17/2017.
 */
package com.edeqa.helpers.interfaces;

public interface Runnable2<T,U> {
    void call(T arg1, U arg2);
}
