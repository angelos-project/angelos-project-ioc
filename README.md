# Inversion of Control  for Kotlin with DSL
IoC implementation for Kotlin/Multiplatform with multithreading.

[![Build Status](https://app.travis-ci.com/angelos-project/angelos-project-ioc.svg?branch=master)](https://app.travis-ci.com/angelos-project/angelos-project-ioc)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c285f426e9ed4797989b68044bffdb92)](https://www.codacy.com/gh/angelos-project/angelos-project-ioc/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=angelos-project/angelos-project-ioc&amp;utm_campaign=Badge_Grade)

```kotlin
import org.angelos.ioc.Config
import org.angelos.ioc.Container
import org.angelos.ioc.Module

// Implementing a custom module type for the container, 
// which represents an item.
open class ExampleModule : Module

// Creating modules that can be dispatched as services.
class Module1 : ExampleModule()

class Module2 : ExampleModule()

class Module3(val one: Module1, val two: Module2) : ExampleModule()


// The IoC implementation.
class ExampleContainer : Container<String, ExampleModule>() {
    operator fun invoke(block: ExampleContainer.() -> Config<String, ExampleModule>) = config { block() }
}


fun main() {
    val ioc = ExampleContainer()
    ioc {
        mapOf(
            "one" to { Module1() },
            "two" to { Module2() },
            "three" to { Module3(get("one"), get("two")) }
        )
    }

    val three: Module3 = ioc["three"]
}
```