package g000sha256.reduktor.core.common

import g000sha256.reduktor.core.Actions
import g000sha256.reduktor.core.Environment
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsSideEffectTest {

    @Test
    fun `invoke - has news - news inherits from action`() {
        // GIVEN
        var result: Int? = null
        val newsSideEffect = NewsSideEffect<Number, String, Int> { result = it }
        val environment = mock<Environment<Number>>()
        val actions = mock<Actions<Number>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = 5, state = "State") }

        // THEN
        Assert.assertEquals(5, result)
    }

    @Test
    fun `invoke - has no news - news is not inherited from action`() {
        // GIVEN
        var result: Int? = null
        val newsSideEffect = NewsSideEffect<Number, String, Int> { result = it }
        val environment = mock<Environment<Number>>()
        val actions = mock<Actions<Number>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = 5.0, state = "State") }

        // THEN
        Assert.assertNull(result)
    }

    @Test
    fun `invoke - has news - mapper returned the value`() {
        // GIVEN
        var result: String? = null
        val newsSideEffect = NewsSideEffect<String, String, String>(
            mapper = { action, state -> "$action $state" },
            newsCallback = { result = it }
        )
        val environment = mock<Environment<String>>()
        val actions = mock<Actions<String>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = "Action", state = "State") }

        // THEN
        Assert.assertEquals("Action State", result)
    }

    @Test
    fun `invoke - has no news - mapper did not return a value`() {
        // GIVEN
        var hasResult = false
        val newsSideEffect = NewsSideEffect<String, String, String>(
            mapper = { _, _ -> null },
            newsCallback = { hasResult = true }
        )
        val environment = mock<Environment<String>>()
        val actions = mock<Actions<String>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = "Action", state = "State") }

        // THEN
        Assert.assertFalse(hasResult)
    }

}