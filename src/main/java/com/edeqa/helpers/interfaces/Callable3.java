/**
 * Part of Waytous <http://waytous.net>
 * Copyright (C) Edeqa LLC <http://www.edeqa.com>
 *
 * Created 5/17/2017.
 */
package com.edeqa.helpers.interfaces;

/**
 * Created 10/10/17.
 */

public interface Callable3<T,U,V,W> {
    T call(U arg1, V arg2, W arg3);
}
