package com.example.weis.ui.fragment.mainFragments.focusFragments.sub

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
import com.example.weis.modals.Goal

class PlayFragment(private val goal : Goal? = null) : Fragment() {


    private lateinit var binding : FragmentPlayBinding
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
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var state  = MediaState.OFF
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mediaPlayer = MediaPlayer.create(context, R.raw.rain_car_win)
        mediaPlayer.isLooping = false

        if(goal != null){
            binding.textGoalComp.text = goal.goal
        }else{
            binding.textGoalComp.visibility = View.GONE
        }

        val musicOptions : ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.music_list_item, musicList.keys.toTypedArray())
        binding.musicOptions.setAdapter(musicOptions)
        binding.musicOptions.onItemClickListener = AdapterView.OnItemClickListener {
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

        binding.musicOptions.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_text_box,
                null
            )
        )
        binding.musicOptions.setDropDownBackgroundResource(R.color.translucent)


        binding.imgBtnPlayPause.setOnClickListener{
            if(state == MediaState.OFF){
                state = MediaState.ON
                mediaPlayer.start()
                binding.imgBtnPlayPause.setImageResource(R.drawable.ic_pause)
            }else{
                state = MediaState.OFF
                mediaPlayer.pause()
                binding.imgBtnPlayPause.setImageResource(R.drawable.ic_play)
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