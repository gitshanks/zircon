package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.platform.util.SystemUtils

data class FadeIn(private val steps: Int = 20,
                  private val timeMs: Long = 2000,
                  private val glowOnFinalStep: Boolean = true) : TileTransformModifier<CharacterTile> {

    private var currentStep = 1
    private var delay: Long = timeMs / steps
    private var lastRender: Long = Long.MIN_VALUE
    private lateinit var currentTile: CharacterTile

    override fun generateCacheKey(): String {
        return "Modifier.FadeIn.$currentStep"
    }

    override fun transform(tile: CharacterTile): CharacterTile {
        if (isFirstRender()) {
            lastRender = SystemUtils.getCurrentTimeMs()
            currentTile = generateTile(tile)
        }
        return if (currentStep == steps) {
            if (glowOnFinalStep)
                tile.withModifiers(Glow()).asCharacterTile().get()
            else
                tile
        } else {
            val now = SystemUtils.getCurrentTimeMs()
            if (now - lastRender > delay) {
                lastRender = now
                currentTile = generateTile(tile)
                currentStep++
            }
            currentTile
        }
    }

    private fun generateTile(tile: CharacterTile): CharacterTile {
        return tile.withBackgroundColor(tile.backgroundColor
                .darkenByPercent(1.0.minus(currentStep.toDouble().div(steps))))
                .withForegroundColor(tile.foregroundColor
                        .darkenByPercent(1.0.minus(currentStep.toDouble().div(steps))))
    }

    private fun isFirstRender() = lastRender == Long.MIN_VALUE

}
