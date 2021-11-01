package g000sha256.reduktor.core.common

import g000sha256.reduktor.core.Actions
import g000sha256.reduktor.core.Environment
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ActionsInitializerTest {

    @Test
    fun `invoke - actions are same`() {
        // GIVEN
        val actionsInitializer = ActionsInitializer<String, String>()
        val environment = mock<Environment<String>>()
        val actions = mock<Actions<String>>()
        whenever(environment.actions).thenReturn(actions)

        // WHEN
        actionsInitializer.apply { environment.invoke(initialState = "InitialState") }

        // THEN
        Assert.assertSame(actions, actionsInitializer.actions)
    }

    @Test
    fun `get actions - throws an illegal state exception - invoke method was not called`() {
        // GIVEN
        val actionsInitializer = ActionsInitializer<String, String>()

        // WHEN
        val result = runCatching { actionsInitializer.actions }

        // THEN
        Assert.assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()!!
        Assert.assertEquals(IllegalStateException::class.java, exception.javaClass)
        Assert.assertEquals("Initializer has not been called yet", exception.message)
    }

    @Test
    fun `invoke - throws an illegal state exception - invoke method was called 2 times`() {
        // GIVEN
        val actionsInitializer = ActionsInitializer<String, String>()
        val environment = mock<Environment<String>>()
        val actions = mock<Actions<String>>()
        whenever(environment.actions).thenReturn(actions)
        actionsInitializer.apply { environment.invoke(initialState = "InitialState") }

        // WHEN
        val result = runCatching { actionsInitializer.apply { environment.invoke(initialState = "InitialState") } }

        // THEN
        Assert.assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()!!
        Assert.assertEquals(IllegalStateException::class.java, exception.javaClass)
        Assert.assertEquals("Initializer has already been called", exception.message)
    }

}