package com.app.reelsapp.core.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import com.app.reelsapp.core.presentation.theme.ReelsAppTheme
import com.app.reelsapp.reels.presentation.component.CacheManager
import com.app.reelsapp.reels.presentation.component.ReelBottomContent
import com.app.reelsapp.reels.presentation.component.ReelTopBar
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingConfig
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val userID = "audo"
    private val username = "audo"
    private val isHost = true
    private val liveID = "123"
    private val appID = 407143386L
    private val appSign = "a6acca0ecd9decf8febb32b1ea5cc23790d8f9c6d0394dca3fa15c5734fd751d"

    // تأكد من الحصول على هذه القيم من ZEGOCLOUD Console
    private val serverSecret = "7c2853750f5b47d14b74dc46ff254551" // من Cloud Recording settings
    private val cloudRecordingApiUrl = "https://api.zegocloud.com/v1" // الـ URL الصحيح

    // Room ID يجب أن يكون نفسه المستخدم في LiveStreaming
    private val roomID = liveID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // بدء التسجيل فقط عند كون المستخدم host
        if (isHost) {
            startCloudRecording()
        }

        setContent {
            ReelsAppTheme {
                val config = if (isHost) {
                    ZegoUIKitPrebuiltLiveStreamingConfig.host(true)
                } else {
                    ZegoUIKitPrebuiltLiveStreamingConfig.audience(true)
                }

                val fragmentManager = remember {
                    this@MainActivity.supportFragmentManager
                }

                val fragment = remember {
                    ZegoUIKitPrebuiltLiveStreamingFragment.newInstance(
                        appID, appSign, userID, username, liveID, config
                    )
                }

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            FrameLayout(context).apply {
                                id = View.generateViewId()
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                )
                            }
                        },
                        update = {
                            fragmentManager.beginTransaction().replace(it.id, fragment).commit()
                        }
                    )

                    ReelTopBar(
                        modifier = Modifier
                            .padding(vertical = 50.dp, horizontal = 20.dp)
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        name = "Product Name",
                        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuNhTZJTtkR6b-ADMhmzPvVwaLuLdz273wvQ&s",
                        isFollow = true,
                    ) {}

                    ReelBottomContent(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 40.dp),
                        productName = "Product Name",
                        productDescription = "Product Description"
                    )
                }
            }
        }
    }

    private fun startCloudRecording() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

                val json = JSONObject().apply {
                    put("app_id", appID)
                    put("room_id", roomID)
                    put("user_id", "recording_user_${System.currentTimeMillis()}")
                    put("output", JSONObject().apply {
                        put("format", "mp4")
                        put("storage", JSONObject().apply {
                            // تأكد من إعداد التخزين في ZEGOCLOUD Console
                            put("type", "s3") // أو أي نوع تخزين تستخدمه
                        })
                    })
                    // إعدادات إضافية للتسجيل المختلط
                    put("mix", JSONObject().apply {
                        put("enabled", true)
                        put("layout", JSONObject().apply {
                            put("type", "grid")
                        })
                    })
                }

                val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

                val request = Request.Builder()
                    .url("$cloudRecordingApiUrl/cloud-recording/record/start")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Basic $serverSecret") // تنسيق التوثيق الصحيح
                    .post(requestBody)
                    .build()

                Log.d("CloudRecording", "Starting recording...")
                Log.d("CloudRecording", "Request URL: ${request.url}")
                Log.d("CloudRecording", "Request body: $json")

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful) {
                    Log.i("CloudRecording", "✅ Cloud recording started successfully")
                    Log.d("CloudRecording", "Response: $responseBody")

                    // حفظ معرف المهمة لاستخدامه لاحقاً
                    responseBody?.let { body ->
                        val jsonResponse = JSONObject(body)
                        val taskId = jsonResponse.optString("task_id")
                        if (taskId.isNotEmpty()) {
                            Log.d("CloudRecording", "Task ID: $taskId")
                            // يمكنك حفظ task_id لاستخدامه في إيقاف التسجيل
                        }
                    }
                } else {
                    Log.e("CloudRecording", "❌ Failed to start recording: ${response.code}")
                    Log.e("CloudRecording", "Error body: $responseBody")

                    when (response.code) {
                        401 -> Log.e("CloudRecording", "Authentication failed - check server secret")
                        403 -> Log.e("CloudRecording", "Permission denied - enable Cloud Recording in console")
                        400 -> Log.e("CloudRecording", "Bad request - check request parameters")
                        else -> Log.e("CloudRecording", "Unexpected error code: ${response.code}")
                    }
                }
            } catch (e: java.net.UnknownHostException) {
                Log.e("CloudRecording", "❌ Network error: Unable to reach ZEGOCLOUD API")
                Log.e("CloudRecording", "Check internet connection and DNS settings")
            } catch (e: Exception) {
                Log.e("CloudRecording", "❌ Exception: ${e.localizedMessage}", e)
            }
        }
    }

    private fun stopCloudRecording(taskId: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

                val json = JSONObject().apply {
                    put("task_id", taskId)
                }

                val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

                val request = Request.Builder()
                    .url("$cloudRecordingApiUrl/cloud-recording/record/stop")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Basic $serverSecret")
                    .post(requestBody)
                    .build()

                Log.d("CloudRecording", "Stopping recording for task: $taskId")

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful) {
                    Log.i("CloudRecording", "✅ Recording stopped successfully")
                    Log.d("CloudRecording", "Response: $responseBody")
                } else {
                    Log.e("CloudRecording", "❌ Failed to stop recording: ${response.code}")
                    Log.e("CloudRecording", "Error: $responseBody")
                }
            } catch (e: Exception) {
                Log.e("CloudRecording", "❌ Error stopping recording: ${e.localizedMessage}", e)
            }
        }
    }

    private fun queryRecordingTasks() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

                val request = Request.Builder()
                    .url("$cloudRecordingApiUrl/cloud-recording/record/tasks?app_id=$appID&room_id=$roomID")
                    .addHeader("Authorization", "Basic $serverSecret")
                    .get()
                    .build()

                Log.d("RecordedVideos", "Querying recording tasks...")

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful) {
                    Log.i("RecordedVideos", "✅ Recording tasks retrieved successfully")
                    Log.d("RecordedVideos", "Tasks: $responseBody")

                    // معالجة قائمة المهام والملفات المسجلة
                    responseBody?.let { body ->
                        val jsonResponse = JSONObject(body)
                        val tasks = jsonResponse.optJSONArray("tasks")
                        tasks?.let { tasksArray ->
                            for (i in 0 until tasksArray.length()) {
                                val task = tasksArray.getJSONObject(i)
                                val taskId = task.optString("task_id")
                                val status = task.optString("status")
                                val fileUrl = task.optString("file_url")

                                Log.d("RecordedVideos", "Task $taskId: Status=$status, URL=$fileUrl")
                            }
                        }
                    }
                } else {
                    Log.e("RecordedVideos", "❌ Failed to query tasks: ${response.code}")
                    Log.e("RecordedVideos", "Error: $responseBody")
                }
            } catch (e: Exception) {
                Log.e("RecordedVideos", "❌ Error querying tasks: ${e.localizedMessage}", e)
            }
        }
    }

    @OptIn(UnstableApi::class)
    override fun onStop() {
        super.onStop()
        CacheManager.release()
    }

    @OptIn(UnstableApi::class)
    override fun onDestroy() {
        super.onDestroy()
        if (isHost) {
            // استعلام عن المهام المسجلة عند إنهاء التطبيق
            queryRecordingTasks()
        }
        CacheManager.clearCache(this)
    }
}