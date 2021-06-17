package com.androiddevs.unittesting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before

import org.junit.Test

class ResourceComparerTest {
        private lateinit var resourceComparer :ResourceComparer
//    private val resourceComparer = ResourceComparer()

    @Before
    fun setUp(){
        resourceComparer = ResourceComparer()
    }

    @After
    fun tearDown(){

    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "unittesting")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceNotSameAsGivenString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "hello")
        assertThat(result).isFalse()
    }
}