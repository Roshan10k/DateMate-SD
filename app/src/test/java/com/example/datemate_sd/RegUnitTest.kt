package com.example.datemate_sd

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class RegUnitTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<Void>

    private lateinit var regRepo: RegRepoImpl

    @Captor
    private lateinit var captor: ArgumentCaptor<OnCompleteListener<Void>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        regRepo = RegRepoImpl(mockAuth)
    }

    @Test
    fun testForgetPassword_Successful() {
        val email = "test@example.com"
        var expectedResult = "Initial Value"

        // Mocking task to simulate successful password reset
        `when`(mockAuth.sendPasswordResetEmail(email)).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(true)

        val callback = { success: Boolean, message: String ->
            expectedResult = message
        }

        regRepo.forgetPassword(email, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Password reset link sent to $email", expectedResult)
    }

    @Test
    fun testForgetPassword_Failed() {
        val email = "test@example.com"
        var expectedResult = "Initial Value" // Define the initial value

        val exception = FirebaseAuthException("Error", "Password reset failed")
        `when`(mockAuth.sendPasswordResetEmail(email)).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(exception)

        val callback = { success: Boolean, message: String ->
            expectedResult = message
        }

        regRepo.forgetPassword(email, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Password reset failed", expectedResult)
    }
}
