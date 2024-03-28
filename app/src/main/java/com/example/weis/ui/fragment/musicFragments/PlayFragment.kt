package com.example.weis.ui.fragment.musicFragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.weis.R
import com.example.weis.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {


    private lateinit var binder : FragmentPlayBinding
    private val handler = android.os.Handler()
    lateinit var mediaPlayer :MediaPlayer
    private val musics = arrayOf("Black Hole", "Rain On Car Window")

    private val musicList = mapOf(
        "Black Hole" to R.raw.black_hole,
        "Rain On Car Window" to R.raw.rain_car_win
    )

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

        mediaPlayer = MediaPlayer.create(context, R.raw.rain_car_win)
        mediaPlayer.isLooping = false

        val musicOptions : ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.music_list_item, musicList.keys.toTypedArray())
        binder.musicOptions.setAdapter(musicOptions)
        binder.musicOptions.onItemClickListener = AdapterView.OnItemClickListener {
            parent, view, position, id ->
            val key : String? = parent?.getItemAtPosition(position)?.toString()
            val resourceId: Int? = key?.let { musicList[it] }
            resourceId?.let {
                mediaPlayer.release()
                mediaPlayer = MediaPlayer.create(context, it)
                mediaPlayer.start()
            } ?: run {
                mediaPlayer = MediaPlayer.create(context, R.raw.rain_car_win)
            }

        }

        binder.musicOptions.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_text_box,
                null
            )
        )
        binder.musicOptions.setDropDownBackgroundResource(R.color.translucent)


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

        val assetFileDescriptor = resources.openRawResourceFd(R.raw.rain_car_win)

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