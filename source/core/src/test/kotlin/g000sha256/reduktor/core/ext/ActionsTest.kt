package g000sha256.reduktor.core.ext

import g000sha256.reduktor.core.Actions
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ActionsTest {

    @Test
    fun post() {
        // GIVEN
        val actions = mock<Actions<String>>()
        val action = "Action"

        // WHEN
        actions post action

        // THEN
        verify(actions).post(action)
    }

    @Test
    fun `post array`() {
        // GIVEN
        val actions = mock<Actions<String>>()
        val array = arrayOf("Action", "Action", "Action")

        // WHEN
        actions post array

        // THEN
        verify(actions).post(array)
    }

    @Test
    fun `post iterable`() {
        // GIVEN
        val actions = mock<Actions<String>>()
        val list = listOf("Action", "Action", "Action")

        // WHEN
        actions post list

        // THEN
        verify(actions).post(list)
    }

}