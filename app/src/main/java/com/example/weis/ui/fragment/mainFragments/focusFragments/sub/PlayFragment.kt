package com.example.weis.ui.fragment.mainFragments.focusFragments.sub

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
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
import com.example.weis.modals.User
import com.example.weis.utils.StoreUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class PlayFragment(private val goal : Goal? = null) : Fragment() {

    //global variables
    private lateinit var binding : FragmentPlayBinding
    private var timer : CountDownTimer? = null
    private var state  = MediaState.ON
    var timeRemaining : Long = 0
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var dbrefUser : DocumentReference
    private var user : User? = null

    //list of musics
    private val musicList = mapOf(
        "Black Hole" to R.raw.black_hole,
        "Rain On Car Window" to R.raw.rain_car_win,
        "Rain On Car Roof" to R.raw.rain_car_roof
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

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("User", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences.getString("user", null)
        user = userDataJson?.let { Gson().fromJson(it, User::class.java)}

        dbrefUser = FirebaseFirestore.getInstance().collection("User").document(user!!.email)
        val dbrefGoal = dbrefUser.collection("Goals")
        //initializing the media player
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
        binding.musicOptions.setText(musicList.keys.toList()[0])

        //if the media has to be run according to the goal set
        if(goal != null){
            binding.textGoalComp.text = goal.goal
            binding.stopwatch.visibility = View.GONE
            binding.textTime.text = "${goal.duration} : 00"
            startOrResumeTimer(goal.duration * 60 * 1000)
        }else{
            binding.textGoalComp.visibility = View.GONE
            binding.textTime.visibility = View.GONE
            binding.stopwatch.start()
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
            if(goal != null) {
                if (timeRemaining > 1000) {
                    if (state == MediaState.OFF) {
                        state = MediaState.ON
                        mediaPlayer.start()
                        if (timer != null) {
                            startOrResumeTimer(timeRemaining)
                        }
                        binding.imgBtnPlayPause.setImageResource(R.drawable.ic_pause)
                    } else {
                        if (mediaPlayer.isPlaying) {
                            state = MediaState.OFF
                            timer?.cancel()
                            mediaPlayer.pause()
                            binding.imgBtnPlayPause.setImageResource(R.drawable.ic_play)

                        }
                    }
                }
            }else{
                if (state == MediaState.OFF){
                    state = MediaState.ON
                    binding.stopwatch.start()
                    mediaPlayer.start()
                    binding.imgBtnPlayPause.setImageResource(R.drawable.ic_pause)
                }else{
                    state = MediaState.OFF
                    binding.stopwatch.stop()
                    mediaPlayer.pause()
                    binding.imgBtnPlayPause.setImageResource(R.drawable.ic_play)
                    Log.d("stop time", "${SystemClock.elapsedRealtime() - binding.stopwatch.base}")
                }
            }

        }


        binding.btnFinishGoal.setOnLongClickListener{
            if(goal != null){
                dbrefGoal.document(goal.id.toString()).update(mapOf("State" to "incomplete"))
                user?.hrsOfFocus = user!!.hrsOfFocus?.plus((goal.duration - timeRemaining) / 1000)
                dbrefUser.update(mapOf("hrsOfFocus" to  user?.hrsOfFocus))
                StoreUser.saveData(user!!, requireActivity())
            }else{
                user?.hrsOfFocus = (SystemClock.elapsedRealtime() - binding.stopwatch.base) / 1000
                dbrefUser.update(mapOf("hrsOfFocus" to user?.hrsOfFocus))
                StoreUser.saveData(user!!, requireActivity())
            }
            stopMusic()
            parentFragmentManager.popBackStack()
                true
        }
    }


    //function to start or resume timer
    private fun startOrResumeTimer(durationInMillis: Long) {
        timer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textTime.text = (String.format("%02d",(millisUntilFinished / 1000) / 60)) + " : " + String.format("%02d",(millisUntilFinished / 1000) % 60)
                timeRemaining = millisUntilFinished
            }

            override fun onFinish() {
                binding.textTime.text = "00:00"
                binding.imgBtnPlayPause.setImageResource(R.drawable.ic_play)
                stopMusic()
                user?.tasksDone = user?.tasksDone?.plus(1)
                dbrefUser.update(mapOf("taskedCompleted" to user?.tasksDone))
                StoreUser.saveData(user!!, requireActivity())
                val newFragment = GoalFinishedFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragContFocus, newFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }.start()
    }

    //function to stop the sound
    private fun stopMusic() {
        state = MediaState.OFF
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    //enum class to define the state of the sound
    enum class MediaState {
        ON,
        OFF
    }

}