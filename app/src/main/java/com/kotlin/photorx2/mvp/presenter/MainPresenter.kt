package com.kotlin.photorx2.mvp.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.kotlin.photorx2.mvp.model.FileInfo
import com.kotlin.photorx2.mvp.view.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class MainPresenter(val model: FileInfo) : MvpPresenter<MainView>()  {

    open fun convertFile() {
        val converterDisposable = CompositeDisposable()
        converterDisposable?.add(
            convertJpgToPng(model.inPath, model.outPath)
                .delay(3, TimeUnit.SECONDS)
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showMessage("$it converted to png.")
                }, {
                    viewState.showMessage("${it.message}")
                })
        )
    }

    private fun convertJpgToPng(pathToBitmap: String, pathImageOutput: String): Single<String> {
        return Single.fromCallable {
                val imageOutputStream = FileOutputStream(pathImageOutput)
               val bitmap = BitmapFactory.decodeFile(pathToBitmap)
               if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageOutputStream)) {
                   BitmapFactory.decodeFile(pathImageOutput)
                   imageOutputStream.close()
                   return@fromCallable (pathImageOutput)
               } else {
                   throw Exception("Conversion problem")
               }
           }
    }
}