package com.example.weis.ui.fragment.mainFragments.focusFragments.sub

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
import java.util.Timer
import kotlin.concurrent.schedule

class PlayFragment(private val goal : Goal? = null) : Fragment() {

    //global variables
    private lateinit var binding : FragmentPlayBinding
//    private val handler = android.os.Handler()
    private lateinit var soundPool : SoundPool
    private lateinit var timer : CountDownTimer
    private var state  = MediaState.ON
    var timeRemaining : Long = 0

    private lateinit var mediaPlayer: MediaPlayer


    //list of musics
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //initializing the media player
//        val fileDescriptor = context?.resources!!.openRawResourceFd(R.raw.black_hole)?.fileDescriptor


        mediaPlayer = MediaPlayer.create(context, R.raw.black_hole)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        //setting the background of the dropdown and the background tint
        binding.musicOptions.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_text_box,
                null
            )
        )
        binding.musicOptions.setDropDownBackgroundResource(R.color.translucent)

        //if the media has to be run according to the goal set
        if(goal != null){
            binding.textGoalComp.text = goal.goal
            binding.textTime.text = "${goal.duration} : 00"
            startOrResumeTimer(goal.duration * 60 * 1000)
        }else{
            binding.textGoalComp.visibility = View.GONE
        }

        //setting the array adapter for the drop down menu
        val musicOptions : ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.music_list_item, musicList.keys.toTypedArray())
        binding.musicOptions.setAdapter(musicOptions)

        //listener for the change in options in the drop down
        binding.musicOptions.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            val key : String? = parent?.getItemAtPosition(position)?.toString()
            val resourceId: Int? = key?.let { musicList[it] }
            resourceId?.let {
                this.mediaPlayer.release()
                mediaPlayer = MediaPlayer.create(context, it)
                mediaPlayer.start()
            } ?: run {
                mediaPlayer = MediaPlayer.create(context, R.raw.black_hole)
                mediaPlayer.start()
            }

        }

        //handling the pause and resume of the sound
        binding.imgBtnPlayPause.setOnClickListener{
            if(timeRemaining > 1000) {
                if (state == MediaState.OFF) {
                    state = MediaState.ON

                    mediaPlayer.start()
                    startOrResumeTimer(timeRemaining)
                    binding.imgBtnPlayPause.setImageResource(R.drawable.ic_pause)
                } else {
                    if (mediaPlayer.isPlaying) {
                        state = MediaState.OFF
                        timer.cancel()
                        mediaPlayer.pause()
                        binding.imgBtnPlayPause.setImageResource(R.drawable.ic_play)

                    }
                }
            }
        }




        //keeping the sound in loop if the time is left
//        if (timeRemaining > 1000){
//            Timer().schedule(6000) {
//                this@PlayFragment.mediaPlayer.seekTo(0)
//            }
//        }else{
//            this.mediaPlayer.release()
//        }
//        startPeriodicTask()

//        mediaPlayer.setOnCompletionListener {
//            mediaPlayer.apply {
//                start()
//            }
//        }

//        if(goal != null){
//            handler.postDelayed({
//                stopMusic()
//            }, (goal.duration * 60 * 1000).toLong())
//        }
    }


    //function to start or resume timer
    private fun startOrResumeTimer(durationInMillis: Long) {
        timer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textTime.text = ((millisUntilFinished / 1000) / 60).toString() + " : " + ((millisUntilFinished / 1000) % 60).toString()
                timeRemaining = millisUntilFinished
            }

            override fun onFinish() {
                binding.textTime.text = "00:00"
                binding.imgBtnPlayPause.setImageResource(R.drawable.ic_play)
                stopMusic()
            }
        }.start()
    }

    //function to stop the sound
    private fun stopMusic() {
        state = MediaState.OFF
        mediaPlayer.stop()
        mediaPlayer.release()
    }


    //variable to store object and run in loop
//    private val runnable = object : Runnable {
//        override fun run() {
//            if(soundPool.isPlaying) {
//                soundPool.seekTo(0)
//                handler.postDelayed(this, 6000)
//            }
//        }
//    }

//    private fun startPeriodicTask() {
//        handler.postDelayed(runnable, 6000)
//    }

    //enum class to define the state of the sound
    enum class MediaState {
        ON,
        OFF
    }

}