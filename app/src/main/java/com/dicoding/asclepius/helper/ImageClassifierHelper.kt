package com.dicoding.asclepius.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.channels.FileChannel

class ImageClassifierHelper(private val context: Context) {

    private lateinit var tflite: Interpreter

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val assetFileDescriptor = context.assets.openFd("model.tflite")
        val fileInputStream = assetFileDescriptor.createInputStream()
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        tflite = Interpreter(fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength))
    }

    fun classifyStaticImage(imageUri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val result = runInference(resizedBitmap)
        return result
    }

    private fun runInference(bitmap: Bitmap): String {
        return "Result of Model Inference"
    }
}