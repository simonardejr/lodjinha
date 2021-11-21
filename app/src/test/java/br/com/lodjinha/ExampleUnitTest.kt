package br.com.lodjinha

import org.junit.Test
import org.junit.Assert.*
import com.google.common.truth.Truth.assertThat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    fun sum(x: Int, y: Int): Int {
        return x + y
    }

    @Test
    fun addition_isCorrect() {
        val expected = 3
        val result = sum(1, 2)
        assertThat(result).isEqualTo(expected)
        // assertEquals(expected, result)
    }
}