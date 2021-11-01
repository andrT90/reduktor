package g000sha256.reduktor.rxjava2.ext

import io.reactivex.Completable
import io.reactivex.processors.PublishProcessor
import org.junit.Assert
import org.junit.Test

class CompletableTest {

    @Test
    fun test1() {
        // GIVEN
        var hasError = false
        var isCompleted = false
        var isFinished = false
        val task = Completable
            .complete()
            .toTask(
                onError = { hasError = true },
                onComplete = { isCompleted = true }
            )

        // WHEN
        task.start { isFinished = true }

        // THEN
        Assert.assertFalse(hasError)
        Assert.assertTrue(isCompleted)
        Assert.assertTrue(isFinished)
    }

    @Test
    fun test2() {
        // GIVEN
        var hasError = false
        var isCompleted = false
        var isFinished = false
        val throwable = Throwable()
        val task = Completable
            .error(throwable)
            .toTask(
                onError = { hasError = true },
                onComplete = { isCompleted = true }
            )

        // WHEN
        task.start { isFinished = true }

        // THEN
        Assert.assertFalse(isCompleted)
        Assert.assertTrue(hasError)
        Assert.assertTrue(isFinished)
    }

    @Test
    fun test3() {
        // GIVEN
        val task = PublishProcessor
            .create<Unit>()
            .ignoreElements()
            .toTask(
                onError = {}
            )

        // WHEN
        val result = runCatching { task.cancel() }

        // THEN
        Assert.assertTrue(result.isFailure)
        val actualThrowable = result.exceptionOrNull()!!
        Assert.assertEquals(IllegalStateException::class.java, actualThrowable.javaClass)
        Assert.assertEquals("Task has not been started yet", actualThrowable.message)
    }

}