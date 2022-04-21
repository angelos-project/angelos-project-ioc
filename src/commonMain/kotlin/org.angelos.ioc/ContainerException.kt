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

/**
 * Container exception can be thrown in relationship to use of an IoC container.
 *
 * @constructor Exception constructor is for internal use only.
 *
 * @param message The error message that happened in relationship to a container issue.
 */
class ContainerException internal constructor(message: String) : RuntimeException(message){
}