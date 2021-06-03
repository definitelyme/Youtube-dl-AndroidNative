package com.f0rx.youtube_dl_native

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.f0rx.youtube_dl_native.domain.format.Format
import com.f0rx.youtube_dl_native.domain.format.FormatBuilder
import com.f0rx.youtube_dl_native.domain.FormatCommand
import com.f0rx.youtube_dl_native.domain.format.format_fields.*
import com.f0rx.youtube_dl_native.repositories.DownloadRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val repository: DownloadRepository by inject()

    private val scope = CoroutineScope(Job())
    private val handler = CoroutineExceptionHandler { _, e ->
        Log.w(ILibrary.kTag, e.message ?: "failed to initialize youtubedl-android", e)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder = FormatBuilder.define(Format.WorstVideo) {
            binder = NumericMetaFieldBuilder.define(
                field = NumericMetaField.Height,
                operator = NumericOperator.LessThanOrEqualTo,
                value = 420
            ) {}
        }

        val builder2 = FormatBuilder.define(Format.WorstAudio) {
            binder = StringMetaFieldBuilder.define(
                field = StringMetaField.Extension,
                operator = StringOperator.Equals,
                value = MediaExtension.M4A
            ) {}
        }

        val command = FormatCommand.Builder(builder)
            .plus()
            .append(builder2)
            .generate()

        scope.launch(handler) {
            val response = repository.start(
                Uri.parse("https://www.youtube.com/watch?v=KzD3qlnhVZA"),
                commands = arrayListOf(command),
                path = getDir("videos", Context.MODE_PRIVATE),
//                name = "my-awesome-video.3gp",
                callback = { progress, eta ->
                    println("Download Progress: $progress")
                    println("ETA in Seconds: ${eta}sec")
                },
            )

            println("The output here")
            println(response.commands)
//            println(response.out)
        }
    }
}