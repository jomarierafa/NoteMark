package com.jvrcoding.notemark.core.presentation.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange

fun Modifier.customTouch(
    onScroll: () -> Unit = {},
    onTap: () -> Unit = {}
) = this.then(
    Modifier.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                awaitPointerEvent(PointerEventPass.Initial)
                    .changes.firstOrNull { it.changedToDown() } ?: continue

                var isTap = true
                while (true) {
                    val event = awaitPointerEvent(PointerEventPass.Initial)
                    val change = event.changes.first()

                    if (change.changedToUp()) {
                        break
                    }

                    if (change.positionChange().getDistance() > 10f) {
                        isTap = false // It’s a scroll or drag
                        break
                    }
                }

                if (isTap) {
                    onTap()
                } else {
                    onScroll()
                }
            }
        }
    }
)