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

import kotlin.test.*


open class TestModule: Module

class FirstModule: TestModule()

class SecondModule: TestModule()

class ThirdModule(val first: FirstModule, val second: SecondModule): TestModule()

class FourthModule(val third: ThirdModule): TestModule()


class TestContainer: Container<String, TestModule>() {
    operator fun invoke (block: TestContainer.() -> Config<String, TestModule>) = config { block() }
}


/**
 * Container unit test
 *
 * @constructor Create empty Container test
 */
class ContainerTest {

    lateinit var ioc: TestContainer

    /**
     * Set up for unit testing.
     *
     */
    @BeforeTest
    fun setUp(){
        ioc = TestContainer()
        ioc {
            mapOf(
                "first" to { FirstModule() },
                "second" to { SecondModule() },
                "third" to { ThirdModule(get("first"), get("second")) },
                "fourth" to { FourthModule(get("third")) },
            )
        }
    }

    /**
     * Unit test for configuring an example IoC.
     */
    @Test
    fun config() {
        val fourth: FourthModule = ioc["fourth"]
        val third: ThirdModule = ioc["third"]
        val second: SecondModule = ioc["second"]
        val first: FirstModule = ioc["first"]

        assertNotNull(first)
        assertNotNull(second)
        assertEquals(third.first, first)
        assertEquals(third.second, second)
        assertEquals(fourth.third, third)

        assertFailsWith<ContainerException>{
            val fifth: TestModule = ioc["fifth"]
            assertNotNull(fifth) // <-- Should not assert
        }
    }
}