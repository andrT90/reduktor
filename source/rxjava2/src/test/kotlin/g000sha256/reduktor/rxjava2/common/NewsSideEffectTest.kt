package g000sha256.reduktor.rxjava2.common

import g000sha256.reduktor.core.Actions
import g000sha256.reduktor.core.Environment
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsSideEffectTest {

    @Test
    fun `invoke - has news - news inherits from action`() {
        // GIVEN
        val newsSideEffect = NewsSideEffect<Number, String, Int>()
        val testSubscriber = newsSideEffect.news.test()
        val environment = mock<Environment<Number>>()
        val actions = mock<Actions<Number>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = 5, state = "State") }

        // THEN
        testSubscriber.assertNotComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValues(5)
    }

    @Test
    fun `invoke - has no news - news is not inherited from action`() {
        // GIVEN
        val newsSideEffect = NewsSideEffect<Number, String, Int>()
        val testSubscriber = newsSideEffect.news.test()
        val environment = mock<Environment<Number>>()
        val actions = mock<Actions<Number>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = 5.0, state = "State") }

        // THEN
        testSubscriber.assertNotComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertNoValues()
    }

    @Test
    fun `invoke - has news - mapper returned the value`() {
        // GIVEN
        val newsSideEffect = NewsSideEffect<String, String, String> { action, state -> "$action $state" }
        val testSubscriber = newsSideEffect.news.test()
        val environment = mock<Environment<String>>()
        val actions = mock<Actions<String>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = "Action", state = "State") }

        // THEN
        testSubscriber.assertNotComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValues("Action State")
    }

    @Test
    fun `invoke - has no news - mapper did not return a value`() {
        // GIVEN
        val newsSideEffect = NewsSideEffect<String, String, String> { _, _ -> null }
        val testSubscriber = newsSideEffect.news.test()
        val environment = mock<Environment<String>>()
        val actions = mock<Actions<String>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        newsSideEffect.apply { environment.invoke(action = "Action", state = "State") }

        // THEN
        testSubscriber.assertNotComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertNoValues()
    }

}