package g000sha256.reduktor.coroutines.common

import g000sha256.reduktor.core.Actions
import g000sha256.reduktor.core.Environment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsSideEffectTest {

    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

    @Test
    fun `invoke - has news - news inherits from action`() {
        // GIVEN
        val newsSideEffect = NewsSideEffect<Number, String, Int>()
        val actualList = mutableListOf<Int>()
        coroutineScope.launch { newsSideEffect.news.toCollection(actualList) }

        val environment = mock<Environment<Number>>()
        val actions = mock<Actions<Number>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = 5, state = "State") }

        // THEN
        val expectedList = listOf(5)
        Assert.assertEquals(expectedList, actualList)
    }

    @Test
    fun `invoke - has no news - news is not inherited from action`() {
        // GIVEN
        val newsSideEffect = NewsSideEffect<Number, String, Int>()
        val actualList = mutableListOf<Int>()
        coroutineScope.launch { newsSideEffect.news.toCollection(actualList) }
        val environment = mock<Environment<Number>>()
        val actions = mock<Actions<Number>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = 5.0, state = "State") }

        // THEN
        val expectedList = emptyList<Int>()
        Assert.assertEquals(expectedList, actualList)
    }

    @Test
    fun `invoke - has news - mapper returned the value`() {
        // GIVEN
        val newsSideEffect = NewsSideEffect<String, String, String> { action, state -> "$action $state" }
        val actualList = mutableListOf<String>()
        coroutineScope.launch { newsSideEffect.news.toCollection(actualList) }
        val environment = mock<Environment<String>>()
        val actions = mock<Actions<String>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = "Action", state = "State") }

        // THEN
        val expectedList = listOf("Action State")
        Assert.assertEquals(expectedList, actualList)
    }

    @Test
    fun `invoke - has no news - mapper did not return a value`() {
        // GIVEN
        val newsSideEffect = NewsSideEffect<String, String, String> { _, _ -> null }
        val actualList = mutableListOf<String>()
        coroutineScope.launch { newsSideEffect.news.toCollection(actualList) }
        val environment = mock<Environment<String>>()
        val actions = mock<Actions<String>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = "Action", state = "State") }

        // THEN
        val expectedList = emptyList<Int>()
        Assert.assertEquals(expectedList, actualList)
    }

}