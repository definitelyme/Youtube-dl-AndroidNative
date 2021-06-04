package com.f0rx.youtube_dl_native

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.f0rx.youtube_dl_native.domain.FormatCommand
import com.f0rx.youtube_dl_native.domain.ICommand
import com.f0rx.youtube_dl_native.domain.format.Format
import com.f0rx.youtube_dl_native.domain.format.FormatBuilder
import com.f0rx.youtube_dl_native.domain.format.format_fields.*
import com.f0rx.youtube_dl_native.repositories.DownloadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.File

class MainActivity : AppCompatActivity() {
    private val repository: DownloadRepository by inject()

    private val scope = CoroutineScope(Job())

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

        val formatCommand = FormatCommand.Builder(builder)
            .plus()
            .with(builder2)
            .generate()

        val listOfCommands: ArrayList<ICommand> =
            arrayListOf(
                formatCommand,
//                FlagCommand.AllFormats,
//                FlagCommand.PreferFreeFormats
            )

        scope.launch {
            val response = repository.download(
                Uri.parse("https://www.youtube.com/watch?v=KzD3qlnhVZA"),
                name = "another-sample-vid.3gp",
//                downloadPath = getDir("videos", Context.MODE_PRIVATE),
//                commands = listOfCommands,
                downloadPath = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "test"
                ),
                callback = { progress, eta ->
                    println("Download Progress: $progress")
                    println("ETA in (Seconds): ${eta}sec")
                },
            )

            println("The output here")
            response.fold(
                { println(it.message) }
            ) {
                println(it.commands)
                println(it.out)
            }
        }
    }
}