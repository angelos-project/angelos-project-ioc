# Package org.angelos.ioc

This package is used to implement and configure an IoC container and dispatcher.

The container is configurable using Kotlin DSL.

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