package com.yzh.demoapp.activity

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader


/**
 * @author eternity6666@qq.com
 * @since 2023/7/27 16:16
 */
class ColorScaleActivity : AppCompatActivity() {
    private val liveData = MutableLiveData<List<PictureData>>(emptyList())
    private val liveData2 = MutableLiveData<List<PictureData>>(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
            ), 123
        )
        setContent {
            ColorScalePage(
                dataList = liveData,
                dataList2 = liveData2,
                onQueryPicture = this::query1,
                onQueryPicture2 = this::query2,
            )
        }
    }

    private fun query1() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("image/*")
        startActivityForResult(Intent.createChooser(intent, "选择图片"), 2000)
    }

    private fun query2() {
        LoaderManager.getInstance(this).initLoader(123, null, object : LoaderManager.LoaderCallbacks<Cursor> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                return CursorLoader(
                    this@ColorScaleActivity,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )
            }

            override fun onLoaderReset(loader: Loader<Cursor>) {
            }

            override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
                cursor?.let {
                    val result = mutableListOf<PictureData>()
                    while (it.moveToNext()) {
                        val displayNameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                        val descriptionIndex = it.getColumnIndex(MediaStore.Images.Media.DESCRIPTION)
                        val dataIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
                        it.columnNames.forEachIndexed { index, columnName ->
                            Log.i(
                                TAG,
                                "query2: name: $columnName, value: ${it.getString(index)}"
                            )
                        }
                        val pictureData = PictureData(
                            name = if (displayNameIndex > 0) {
                                it.getString(displayNameIndex)
                            } else {
                                ""
                            },
                            desc = if (descriptionIndex > 0) {
                                it.getString(descriptionIndex)
                            } else {
                                ""
                            },
                            byteArray = if (dataIndex > 0) {
                                it.getBlob(dataIndex)
                            } else {
                                byteArrayOf()
                            }
                        )
                        result.add(pictureData)
                        Log.i(TAG, "ColorScalePage: ${pictureData.name}")
                    }
                    liveData2.postValue(result)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DISPLAY_NAME), null, null, null)
            }?.let { cursor ->
                val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                cursor.moveToFirst()
                val pictureData = PictureData(
                    name = cursor.getString(columnIndex),
                    desc = "",
                    byteArray = byteArrayOf()
                )
                liveData.postValue(listOf(pictureData))
                cursor.close()
            }
        }
    }
}

@Composable
private fun ColorScalePage(
    dataList: LiveData<List<PictureData>>,
    dataList2: LiveData<List<PictureData>>,
    onQueryPicture: () -> Unit,
    onQueryPicture2: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onQueryPicture()
            },
        ) {
            Text(text = "查询图片")
        }
        ColorScaleResult(dataList = dataList.observeAsState(emptyList()).value)
        Divider()
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onQueryPicture2()
            }
        ) {
            Text(text = "查询图片2")
        }
        ColorScaleResult(dataList = dataList2.observeAsState(emptyList()).value)
    }
}

@Composable
private fun ColorScaleResult(
    dataList: List<PictureData>,
) {
    LazyColumn(modifier = Modifier.height(100.dp)) {
        items(dataList) { pictureData: PictureData ->
            Text(text = pictureData.name)
        }
    }
}

private class PictureData(
    val name: String,
    val desc: String,
    val byteArray: ByteArray,
)

private const val TAG = "ColorScaleActivity"
