/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angelos.ioc

import co.touchlab.stately.collections.sharedMutableMapOf
import co.touchlab.stately.concurrency.AtomicReference

/**
 * Abstract container that should implement a certain container type using a specific implementation of Module interface.
 *
 * @param N Key type to be used with certain container implementation, String is favorable.
 * @param M Submodule type that will be used as items inside the container implementation. Must inherit from Module.
 * @constructor Create empty Container
 */
abstract class Container<N, M: Module> {
    private val config = AtomicReference<Config<N, M>>(mapOf())
    private val modules = sharedMutableMapOf<N, M>()

    fun config(block: () -> Config<N, M>) { config.set(block()) }

    private fun <M2: M> reg(key: N, module: M2): M2 {
        modules[key] = module
        return module
    }

    @Suppress("UNCHECKED_CAST")
    open operator fun <M2: M>get(key: N): M2 = when(key) {
        in modules -> modules[key]!!
        in config.get() -> reg(key, config.get()[key]!!(key))
        else -> throw ContainerException("$key is not configured!")
    } as M2
}