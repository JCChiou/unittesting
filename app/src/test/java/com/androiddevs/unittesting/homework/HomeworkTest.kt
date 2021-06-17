package com.androiddevs.unittesting.homework

import org.junit.Test

import com.google.common.truth.Truth.assertThat

class HomeworkTest {

    @Test
    fun `if n = 0 returns n`() {
        val result = Homework.fib(0)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `if n = 1 returns n`() {
        val result = Homework.fib(1)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `if n = 2 returns fib(n-1) + fib(n-2)`() {
        val result = Homework.fib(2)
        assertThat(result).isEqualTo(Homework.fib(2-1)+ Homework.fib(2-2))
    }

    @Test
    fun `if n bigger than 1 returns fib(n-1) + fib(n-2)`(){
        val result = Homework.fib(3)
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `if only 1 ( returns false`(){
        val result = Homework.checkBraces("(")
        assertThat(result).isFalse()
    }

    @Test
    fun `if only 1 ) returns false`(){
        val result = Homework.checkBraces(")")
        assertThat(result).isFalse()
    }

    @Test
    fun `if pair ()  returns true`(){
        val result = Homework.checkBraces("()")
        assertThat(result).isTrue()
    }

    @Test
    fun `if pair )(  returns false`(){
        val result = Homework.checkBraces(")(")
        assertThat(result).isFalse()
    }
}