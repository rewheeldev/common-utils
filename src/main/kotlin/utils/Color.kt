package utils

import java.lang.Float.*

/** A color class, holding the r, g, b and alpha component as floats in the range [0,1]. All methods perform clamping on the
 * internal values after execution.
 *
 */
class Color {
    /** the red, green, blue and alpha components  */
    var r = 0f
    var g = 0f
    var b = 0f
    var a = 0f

    /** Constructs a new Color with all components set to 0.  */
    constructor() {}

    /** @see .rgba8888ToColor
     */
    constructor(rgba8888: Int) {
        Color.Companion.rgba8888ToColor(this, rgba8888)
    }

    /** Constructor, sets the components of the color
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     */
    constructor(r: Float = 0f, g: Float = 0f, b: Float = 0f, a: Float = 1f) {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
        clamp()
    }

    /** Constructs a new color using the given color
     *
     * @param color the color
     */
    constructor(color: Color) {
        set(color)
    }

    /** Sets this color to the given color.
     *
     * @param color the Color
     */
    fun set(color: Color): Color {
        r = color.r
        g = color.g
        b = color.b
        a = color.a
        return this
    }

    /** Multiplies the this color and the given color
     *
     * @param color the color
     * @return this color.
     */
    fun mul(color: Color): Color {
        r *= color.r
        g *= color.g
        b *= color.b
        a *= color.a
        return clamp()
    }

    /** Multiplies all components of this Color with the given value.
     *
     * @param value the value
     * @return this color
     */
    fun mul(value: Float): Color {
        r *= value
        g *= value
        b *= value
        a *= value
        return clamp()
    }

    /** Adds the given color to this color.
     *
     * @param color the color
     * @return this color
     */
    fun add(color: Color): Color {
        r += color.r
        g += color.g
        b += color.b
        a += color.a
        return clamp()
    }

    /** Subtracts the given color from this color
     *
     * @param color the color
     * @return this color
     */
    fun sub(color: Color): Color {
        r -= color.r
        g -= color.g
        b -= color.b
        a -= color.a
        return clamp()
    }

    /** Clamps this Color's components to a valid range [0 - 1]
     * @return this Color for chaining
     */
    fun clamp(): Color {
        if (r < 0) r = 0f else if (r > 1) r = 1f
        if (g < 0) g = 0f else if (g > 1) g = 1f
        if (b < 0) b = 0f else if (b > 1) b = 1f
        if (a < 0) a = 0f else if (a > 1) a = 1f
        return this
    }

    /** Sets this Color's component values.
     *
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     * @param a Alpha component
     *
     * @return this Color for chaining
     */
    operator fun set(r: Float, g: Float, b: Float, a: Float): Color {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
        return clamp()
    }

    /** Sets this color's component values through an integer representation.
     *
     * @return this Color for chaining
     * @see .rgba8888ToColor
     */
    fun set(rgba: Int): Color {
        Color.Companion.rgba8888ToColor(this, rgba)
        return this
    }

    /** Adds the given color component values to this Color's values.
     *
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     * @param a Alpha component
     *
     * @return this Color for chaining
     */
    fun add(r: Float, g: Float, b: Float, a: Float): Color {
        this.r += r
        this.g += g
        this.b += b
        this.a += a
        return clamp()
    }

    /** Subtracts the given values from this Color's component values.
     *
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     * @param a Alpha component
     *
     * @return this Color for chaining
     */
    fun sub(r: Float, g: Float, b: Float, a: Float): Color {
        this.r -= r
        this.g -= g
        this.b -= b
        this.a -= a
        return clamp()
    }

    /** Multiplies this Color's color components by the given ones.
     *
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     * @param a Alpha component
     *
     * @return this Color for chaining
     */
    fun mul(r: Float, g: Float, b: Float, a: Float): Color {
        this.r *= r
        this.g *= g
        this.b *= b
        this.a *= a
        return clamp()
    }

    /** Linearly interpolates between this color and the target color by t which is in the range [0,1]. The result is stored in
     * this color.
     * @param target The target color
     * @param t The interpolation coefficient
     * @return This color for chaining.
     */
    fun lerp(target: Color, t: Float): Color {
        r += t * (target.r - r)
        g += t * (target.g - g)
        b += t * (target.b - b)
        a += t * (target.a - a)
        return clamp()
    }

    /** Linearly interpolates between this color and the target color by t which is in the range [0,1]. The result is stored in
     * this color.
     * @param r The red component of the target color
     * @param g The green component of the target color
     * @param b The blue component of the target color
     * @param a The alpha component of the target color
     * @param t The interpolation coefficient
     * @return This color for chaining.
     */
    fun lerp(r: Float, g: Float, b: Float, a: Float, t: Float): Color {
        this.r += t * (r - this.r)
        this.g += t * (g - this.g)
        this.b += t * (b - this.b)
        this.a += t * (a - this.a)
        return clamp()
    }

    /** Multiplies the RGB values by the alpha.  */
    fun premultiplyAlpha(): Color {
        r *= a
        g *= a
        b *= a
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val color = other as Color
        return toIntBits() == color.toIntBits()
    }

    override fun hashCode(): Int {
        var result = if (r != +0.0f) floatToIntBits(r) else 0
        result = 31 * result + if (g != +0.0f) floatToIntBits(g) else 0
        result = 31 * result + if (b != +0.0f) floatToIntBits(b) else 0
        result = 31 * result + if (a != +0.0f) floatToIntBits(a) else 0
        return result
    }

    /** Packs the color components into a 32-bit integer with the format ABGR and then converts it to a float. Alpha is compressed
     * from 0-255 to use only even numbers between 0-254 to avoid using float bits in the NaN range (see
     * [NumberUtils.intToFloatColor]). Converting a color to a float and back can be lossy for alpha.
     * @return the packed color as a 32-bit float
     */
    fun toFloatBits(): Float {
        val color =
            (255 * a).toInt() shl 24 or ((255 * b).toInt() shl 16) or ((255 * g).toInt() shl 8) or (255 * r).toInt()
        return intBitsToFloat(color and 0xfeffffff.toInt())
    }

    /** Packs the color components into a 32-bit integer with the format ABGR.
     * @return the packed color as a 32-bit int.
     */
    fun toIntBits(): Int {
        return (255 * a).toInt() shl 24 or ((255 * b).toInt() shl 16) or ((255 * g).toInt() shl 8) or (255 * r).toInt()
    }

    /** Returns the color encoded as hex string with the format RRGGBBAA.  */
    override fun toString(): String {
        var value =
            Integer.toHexString((255 * r).toInt() shl 24 or ((255 * g).toInt() shl 16) or ((255 * b).toInt() shl 8) or (255 * a).toInt())
        while (value.length < 8) value = "0$value"
        return value
    }

    /** Sets the RGB Color components using the specified Hue-Saturation-Value. Note that HSV components are voluntary not clamped
     * to preserve high range color and can range beyond typical values.
     * @param h The Hue in degree from 0 to 360
     * @param s The Saturation from 0 to 1
     * @param v The Value (brightness) from 0 to 1
     * @return The modified Color for chaining.
     */
    fun fromHsv(h: Float, s: Float, v: Float): Color {
        val x = (h / 60f + 6) % 6
        val i = x.toInt()
        val f = x - i
        val p = v * (1 - s)
        val q = v * (1 - s * f)
        val t = v * (1 - s * (1 - f))
        when (i) {
            0 -> {
                r = v
                g = t
                b = p
            }

            1 -> {
                r = q
                g = v
                b = p
            }

            2 -> {
                r = p
                g = v
                b = t
            }

            3 -> {
                r = p
                g = q
                b = v
            }

            4 -> {
                r = t
                g = p
                b = v
            }

            else -> {
                r = v
                g = p
                b = q
            }
        }
        return clamp()
    }

    /** Sets RGB components using the specified Hue-Saturation-Value. This is a convenient method for
     * [.fromHsv]. This is the inverse of [.toHsv].
     * @param hsv The Hue, Saturation and Value components in that order.
     * @return The modified Color for chaining.
     */
    fun fromHsv(hsv: FloatArray): Color {
        return fromHsv(hsv[0], hsv[1], hsv[2])
    }

    /** Extract Hue-Saturation-Value. This is the inverse of [.fromHsv].
     * @param hsv The HSV array to be modified.
     * @return HSV components for chaining.
     */
    fun toHsv(hsv: FloatArray): FloatArray {
        val max = Math.max(Math.max(r, g), b)
        val min = Math.min(Math.min(r, g), b)
        val range = max - min
        if (range == 0f) {
            hsv[0] = 0f
        } else if (max == r) {
            hsv[0] = (60 * (g - b) / range + 360) % 360
        } else if (max == g) {
            hsv[0] = 60 * (b - r) / range + 120
        } else {
            hsv[0] = 60 * (r - g) / range + 240
        }
        if (max > 0) {
            hsv[1] = 1 - min / max
        } else {
            hsv[1] = 0f
        }
        hsv[2] = max
        return hsv
    }

    /** @return a copy of this color
     */
    fun cpy(): Color {
        return Color(this)
    }

    /**
     * Red, green, blue and alpha components stacked in an array float.
     */
    fun asFloatArray(type: ColorSchemeType = ColorSchemeType.RGBA): FloatArray {
        return when (type) {
            ColorSchemeType.RGBA -> floatArrayOf(r, g, b, a)
            ColorSchemeType.ARGB -> floatArrayOf(a, r, g, b)
            ColorSchemeType.RGB -> floatArrayOf(r, g, b)
        }
    }

    companion object {
        val WHITE by lazy { Color(1f, 1f, 1f, 1f) }
        val LIGHT_GRAY by lazy { Color(-0x40404001) }
        val GRAY by lazy { Color(0x7f7f7fff) }
        val DARK_GRAY by lazy { Color(0x3f3f3fff) }
        val BLACK by lazy { Color(0f, 0f, 0f, 1f) }

        /** Convenience for frequently used `WHITE.toFloatBits()`  */
        val WHITE_FLOAT_BITS: Float by lazy { Color.Companion.WHITE.toFloatBits() }
        val CLEAR by lazy { Color(0f, 0f, 0f, 0f) }
        val TRANSPARENT by lazy { Color(a = 0f) }
        val BLUE by lazy { Color(0f, 0f, 1f, 1f) }
        val NAVY by lazy { Color(0f, 0f, 0.5f, 1f) }
        val ROYAL by lazy { Color(0x4169e1ff) }
        val SLATE by lazy { Color(0x708090ff) }
        val SKY by lazy { Color(-0x78311401) }
        val CYAN by lazy { Color(0f, 1f, 1f, 1f) }
        val TEAL by lazy { Color(0f, 0.5f, 0.5f, 1f) }
        val GREEN by lazy { Color(0x00ff00ff) }
        val CHARTREUSE by lazy { Color(0x7fff00ff) }
        val LIME by lazy { Color(0x32cd32ff) }
        val FOREST by lazy { Color(0x228b22ff) }
        val OLIVE by lazy { Color(0x6b8e23ff) }
        val YELLOW by lazy { Color(-0xff01) }
        val GOLD by lazy { Color(-0x28ff01) }
        val GOLDENROD by lazy { Color(-0x255adf01) }
        val ORANGE by lazy { Color(-0x5aff01) }
        val BROWN by lazy { Color(-0x74baec01) }
        val TAN by lazy { Color(-0x2d4b7301) }
        val FIREBRICK by lazy { Color(-0x4ddddd01) }
        val RED by lazy { Color(-0xffff01) }
        val SCARLET by lazy { Color(-0xcbe301) }
        val CORAL by lazy { Color(-0x80af01) }
        val SALMON by lazy { Color(-0x57f8d01) }
        val PINK by lazy { Color(-0x964b01) }
        val MAGENTA by lazy { Color(1f, 0f, 1f, 1f) }
        val PURPLE by lazy { Color(-0x5fdf0f01) }
        val VIOLET by lazy { Color(-0x117d1101) }
        val MAROON by lazy { Color(-0x4fcf9f01) }
        /** Sets the specified color from a hex string with the format RRGGBBAA.
         * @see .toString
         */
        /** Returns a new color from a hex string with the format RRGGBBAA.
         * @see .toString
         */
        @JvmOverloads
        fun valueOf(hex: String, color: Color = Color()): Color {
            var hexLocal = hex
            hexLocal = if (hexLocal[0] == '#') hexLocal.substring(1) else hexLocal
            color.r = hexLocal.substring(0, 2).toInt(16) / 255f
            color.g = hexLocal.substring(2, 4).toInt(16) / 255f
            color.b = hexLocal.substring(4, 6).toInt(16) / 255f
            color.a = if (hexLocal.length != 8) 1F else hexLocal.substring(6, 8).toInt(16) / 255f
            return color
        }

        /** Packs the color components into a 32-bit integer with the format ABGR and then converts it to a float. Note that no range
         * checking is performed for higher performance.
         * @param r the red component, 0 - 255
         * @param g the green component, 0 - 255
         * @param b the blue component, 0 - 255
         * @param a the alpha component, 0 - 255
         * @return the packed color as a float
         * @see NumberUtils.intToFloatColor
         */
        fun toFloatBits(r: Int, g: Int, b: Int, a: Int): Float {
            val color = a shl 24 or (b shl 16) or (g shl 8) or r
            return intBitsToFloat(color and 0xfeffffff.toInt())
        }

        /** Packs the color components into a 32-bit integer with the format ABGR and then converts it to a float.
         * @return the packed color as a 32-bit float
         * @see NumberUtils.intToFloatColor
         */
        fun toFloatBits(r: Float, g: Float, b: Float, a: Float): Float {
            val color =
                (255 * a).toInt() shl 24 or ((255 * b).toInt() shl 16) or ((255 * g).toInt() shl 8) or (255 * r).toInt()
            return intBitsToFloat(color and 0xfeffffff.toInt())
        }

        /** Packs the color components into a 32-bit integer with the format ABGR. Note that no range checking is performed for higher
         * performance.
         * @param r the red component, 0 - 255
         * @param g the green component, 0 - 255
         * @param b the blue component, 0 - 255
         * @param a the alpha component, 0 - 255
         * @return the packed color as a 32-bit int
         */
        fun toIntBits(r: Int, g: Int, b: Int, a: Int): Int {
            return a shl 24 or (b shl 16) or (g shl 8) or r
        }

        fun alpha(alpha: Float): Int {
            return (alpha * 255.0f).toInt()
        }

        fun luminanceAlpha(luminance: Float, alpha: Float): Int {
            return (luminance * 255.0f).toInt() shl 8 or (alpha * 255).toInt()
        }

        fun rgb565(r: Float, g: Float, b: Float): Int {
            return (r * 31).toInt() shl 11 or ((g * 63).toInt() shl 5) or (b * 31).toInt()
        }

        fun rgba4444(r: Float, g: Float, b: Float, a: Float): Int {
            return (r * 15).toInt() shl 12 or ((g * 15).toInt() shl 8) or ((b * 15).toInt() shl 4) or (a * 15).toInt()
        }

        fun rgb888(r: Float, g: Float, b: Float): Int {
            return (r * 255).toInt() shl 16 or ((g * 255).toInt() shl 8) or (b * 255).toInt()
        }

        fun rgba8888(r: Float, g: Float, b: Float, a: Float): Int {
            return (r * 255).toInt() shl 24 or ((g * 255).toInt() shl 16) or ((b * 255).toInt() shl 8) or (a * 255).toInt()
        }

        fun argb8888(a: Float, r: Float, g: Float, b: Float): Int {
            return (a * 255).toInt() shl 24 or ((r * 255).toInt() shl 16) or ((g * 255).toInt() shl 8) or (b * 255).toInt()
        }

        fun rgb565(color: Color): Int {
            return (color.r * 31).toInt() shl 11 or ((color.g * 63).toInt() shl 5) or (color.b * 31).toInt()
        }

        fun rgba4444(color: Color): Int {
            return (color.r * 15).toInt() shl 12 or ((color.g * 15).toInt() shl 8) or ((color.b * 15).toInt() shl 4) or (color.a * 15).toInt()
        }

        fun rgb888(color: Color): Int {
            return (color.r * 255).toInt() shl 16 or ((color.g * 255).toInt() shl 8) or (color.b * 255).toInt()
        }

        fun rgba8888(color: Color): Int {
            return (color.r * 255).toInt() shl 24 or ((color.g * 255).toInt() shl 16) or ((color.b * 255).toInt() shl 8) or (color.a * 255).toInt()
        }

        fun argb8888(color: Color): Int {
            return (color.a * 255).toInt() shl 24 or ((color.r * 255).toInt() shl 16) or ((color.g * 255).toInt() shl 8) or (color.b * 255).toInt()
        }

        /** Sets the Color components using the specified integer value in the format RGB565. This is inverse to the rgb565(r, g, b)
         * method.
         *
         * @param color The Color to be modified.
         * @param value An integer color value in RGB565 format.
         */
        fun rgb565ToColor(color: Color, value: Int) {
            color.r = (value and 0x0000F800 ushr 11) / 31f
            color.g = (value and 0x000007E0 ushr 5) / 63f
            color.b = (value and 0x0000001F ushr 0) / 31f
        }

        /** Sets the Color components using the specified integer value in the format RGBA4444. This is inverse to the rgba4444(r, g,
         * b, a) method.
         *
         * @param color The Color to be modified.
         * @param value An integer color value in RGBA4444 format.
         */
        fun rgba4444ToColor(color: Color, value: Int) {
            color.r = (value and 0x0000f000 ushr 12) / 15f
            color.g = (value and 0x00000f00 ushr 8) / 15f
            color.b = (value and 0x000000f0 ushr 4) / 15f
            color.a = (value and 0x0000000f) / 15f
        }

        /** Sets the Color components using the specified integer value in the format RGB888. This is inverse to the rgb888(r, g, b)
         * method.
         *
         * @param color The Color to be modified.
         * @param value An integer color value in RGB888 format.
         */
        fun rgb888ToColor(color: Color, value: Int) {
            color.r = (value and 0x00ff0000 ushr 16) / 255f
            color.g = (value and 0x0000ff00 ushr 8) / 255f
            color.b = (value and 0x000000ff) / 255f
        }

        /** Sets the Color components using the specified integer value in the format RGBA8888. This is inverse to the rgba8888(r, g,
         * b, a) method.
         *
         * @param color The Color to be modified.
         * @param value An integer color value in RGBA8888 format.
         */
        fun rgba8888ToColor(color: Color, value: Int) {
            color.r = (value and -0x1000000 ushr 24) / 255f
            color.g = (value and 0x00ff0000 ushr 16) / 255f
            color.b = (value and 0x0000ff00 ushr 8) / 255f
            color.a = (value and 0x000000ff) / 255f
        }

        /** Sets the Color components using the specified integer value in the format ARGB8888. This is the inverse to the argb8888(a,
         * r, g, b) method
         *
         * @param color The Color to be modified.
         * @param value An integer color value in ARGB8888 format.
         */
        fun argb8888ToColor(color: Color, value: Int) {
            color.a = (value and -0x1000000 ushr 24) / 255f
            color.r = (value and 0x00ff0000 ushr 16) / 255f
            color.g = (value and 0x0000ff00 ushr 8) / 255f
            color.b = (value and 0x000000ff) / 255f
        }

        /** Sets the Color components using the specified integer value in the format ABGR8888.
         * @param color The Color to be modified.
         */
        fun abgr8888ToColor(color: Color, value: Int) {
            color.a = (value and -0x1000000 ushr 24) / 255f
            color.b = (value and 0x00ff0000 ushr 16) / 255f
            color.g = (value and 0x0000ff00 ushr 8) / 255f
            color.r = (value and 0x000000ff) / 255f
        }

        /** Sets the Color components using the specified float value in the format ABGR8888.
         * @param color The Color to be modified.
         */
        fun abgr8888ToColor(color: Color, value: Float) {
            val c: Int = floatToIntColor(value)
            color.a = (c and -0x1000000 ushr 24) / 255f
            color.b = (c and 0x00ff0000 ushr 16) / 255f
            color.g = (c and 0x0000ff00 ushr 8) / 255f
            color.r = (c and 0x000000ff) / 255f
        }

        private fun floatToIntColor(value: Float): Int {
            var intBits = floatToRawIntBits(value)
            intBits = intBits or (((intBits ushr 24) * (255f / 254f)).toInt() shl 24)
            return intBits
        }
    }

    enum class ColorSchemeType {
        ARGB,
        RGBA,
        RGB
    }
}
