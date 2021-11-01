package g000sha256.reduktor.core.ext

import g000sha256.reduktor.core.Task
import g000sha256.reduktor.core.Tasks
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TasksTest {

    @Test
    fun clear() {
        // GIVEN
        val tasks = mock<Tasks>()
        val key = "Key"

        // WHEN
        tasks clear key

        // THEN
        verify(tasks).clear(key)
    }

    @Test
    fun `plus assign`() {
        // GIVEN
        val tasks = mock<Tasks>()
        val task = mock<Task>()

        // WHEN
        tasks += task

        // THEN
        verify(tasks).add(task)
    }

    @Test
    fun set() {
        // GIVEN
        val tasks = mock<Tasks>()
        val key = "Key"
        val task = mock<Task>()

        // WHEN
        tasks[key] = task

        // THEN
        verify(tasks).add(key, task)
    }

}