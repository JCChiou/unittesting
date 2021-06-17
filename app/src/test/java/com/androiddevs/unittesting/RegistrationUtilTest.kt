package com.androiddevs.unittesting


//----
//import org.junit.Assert.* //要使用google的truth lib 要把這個拿掉
import com.google.common.truth.Truth.assertThat //替換Junit的Assert .這個比較易讀
//---
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly password  returns true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Rex",
            "123",
            "123"
        )
        assertThat(result).isTrue()

    }

    @Test
    fun ` username already exist returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Peter",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun ` password is empty returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Peter",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun ` password confirmed not match password  returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Peter",
            "123",
            "321"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun ` password contain less than 2 digits returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Peter",
            "abcde1",
            "abcde1"
        )
        assertThat(result).isFalse()
    }
}