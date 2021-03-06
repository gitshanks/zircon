package org.hexworks.zircon.api.kotlin

import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Runnable

/**
 * Extension function which adapts [RadioButtonGroup.onSelection] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun RadioButtonGroup.onSelection(crossinline fn: (Selection) -> Unit) {
    onSelection(object : Consumer<Selection> {
        override fun accept(value: Selection) {
            fn(value)
        }
    })
}

inline fun RadioButton.onSelected(crossinline fn: () -> Unit) {
    onSelected(object : Runnable {
        override fun run() {
            fn()
        }
    })
}
