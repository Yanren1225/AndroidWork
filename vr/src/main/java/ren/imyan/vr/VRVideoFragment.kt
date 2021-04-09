package ren.imyan.vr

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.vr.sdk.widgets.video.VrVideoEventListener
import com.google.vr.sdk.widgets.video.VrVideoView
import kotlinx.coroutines.*

class VRVideoFragment : Fragment(R.layout.fragment_vr_video) {
    lateinit var vrVideoView: VrVideoView

    private val coroutineScope by lazy {
        CoroutineScope(Dispatchers.Main)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val seekBar: SeekBar = view.findViewById(R.id.seekbar)
        val seekBarText: TextView = view.findViewById(R.id.seekbar_text)
        vrVideoView = view.findViewById(R.id.vr_video)

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val duration = vrVideoView.duration
                    val newPosition = (progress * 0.01f * duration).toLong()
                    vrVideoView.seekTo(newPosition)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        vrVideoView.tag = true
        vrVideoView.setEventListener(object : VrVideoEventListener() {
            override fun onClick() {
                super.onClick()
                vrVideoView.switchState()
            }

            @SuppressLint("SetTextI18n")
            override fun onNewFrame() {
                super.onNewFrame()
                seekBar.max = 100
                val duration = vrVideoView.duration
                val currPosition = vrVideoView.currentPosition
                val percent = (currPosition * 10f / duration + 0.5f).toInt()
                seekBar.progress = percent
                seekBarText.text = "$percent% "
            }

            override fun onLoadSuccess() {
                super.onLoadSuccess()
                Toast.makeText(context, "开始播放", Toast.LENGTH_SHORT).show()
                seekBar.max = 100
                seekBar.progress = 0
                seekBarText.text = "0% "
            }

            override fun onLoadError(errorMessage: String?) {
                super.onLoadError(errorMessage)
                Toast.makeText(context, "播放出错", Toast.LENGTH_SHORT).show()
            }
        })

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                val options = VrVideoView.Options()
                options.inputFormat = VrVideoView.Options.FORMAT_DEFAULT
                options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER
                runCatching {
                    vrVideoView.loadVideoFromAsset("congo.mp4", options)
                }
            }
            Toast.makeText(context, "加载完成", Toast.LENGTH_SHORT).show()
        }
    }

    private fun VrVideoView.switchState(){
        var isPlay = this.tag
        if (isPlay as Boolean) {
            isPlay = false
            this.pauseVideo()
        } else {
            isPlay = true
            this.playVideo()
        }
        this.tag = isPlay
    }

    override fun onResume() {
        super.onResume()
        vrVideoView.resumeRendering()
    }

    override fun onPause() {
        super.onPause()
        vrVideoView.pauseRendering()
    }

    override fun onDestroy() {
        super.onDestroy()
        vrVideoView.shutdown()
        coroutineScope.cancel()
    }
}