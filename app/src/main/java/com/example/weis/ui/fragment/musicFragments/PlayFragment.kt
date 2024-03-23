package com.example.weis.ui.fragment.musicFragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weis.R
import com.example.weis.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {


    private lateinit var binder : FragmentPlayBinding
    private val handler = android.os.Handler()
    lateinit var mediaPlayer :MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binder = FragmentPlayBinding.inflate(inflater, container, false)
        return binder.root
    }

    private var state  = MediaState.OFF
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mediaPlayer = MediaPlayer.create(context, R.raw.rain_car_win2)
        mediaPlayer.isLooping = false

        binder.imgBtnPlayPause.setOnClickListener{
            if(state == MediaState.OFF){
                state = MediaState.ON
                mediaPlayer.start()
                binder.imgBtnPlayPause.setImageResource(R.drawable.ic_pause)
            }else{
                state = MediaState.OFF
                mediaPlayer.pause()
                binder.imgBtnPlayPause.setImageResource(R.drawable.ic_play)
            }
        }
        startPeriodicTask()

        val assetFileDescriptor = resources.openRawResourceFd(R.raw.rain_car_win2)

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.apply {
//                reset()
//                MediaPlayer.create(context, R.raw.rain_car_win2)
                start()
            }
        }
    }

    private val runnable = object : Runnable {
        override fun run() {
            // Call your function here
            mediaPlayer.seekTo(0)

            // Schedule the same runnable to run after 7 seconds
            handler.postDelayed(this, 6000)
        }
    }

    private fun startPeriodicTask() {
        handler.postDelayed(runnable, 6000)
    }


    enum class MediaState {
        ON,
        OFF
    }

}