package com.example.weis.ui.fragment.mainFragments.goalFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.weis.databinding.FragmentGoalSetDialogBinding
import com.example.weis.modals.Goal
import com.example.weis.utils.DialogClickListener
import com.example.weis.utils.ScreenUtils
import com.example.weis.viewModel.GoalViewModel
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import kotlin.math.log

class GoalSetDialogFragment : DialogFragment() {

    private lateinit var binding : FragmentGoalSetDialogBinding
    private var listener: DialogClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGoalSetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        ScreenUtils.initScreenUtils(requireContext().applicationContext)

        // Get the screen width and height
        val screenWidth = ScreenUtils.getScreenWidth()
        val screenHeight = ScreenUtils.getScreenHeight()

        val layoutParams = binding.scrollViewGoalDialog.layoutParams
        layoutParams.width = (screenWidth * 0.85).toInt()
        layoutParams.height = (screenHeight * 0.50).toInt()
        binding.scrollViewGoalDialog.layoutParams = layoutParams

        binding.numPickerSetTimer.minValue = 0
        binding.numPickerSetTimer.maxValue = 60
        binding.datePickerGoal.minDate = getCurrentDate().time
        binding.timePickerGoal.hour = getCurrentDateTime().hour
        binding.timePickerGoal.minute = getCurrentDateTime().minute
        Log.d("date, time", "${getCurrentDateTime().hour}, ${getCurrentDateTime().minute}")

        binding.btnSetGoal.setOnClickListener{
            Log.d("Date", "${binding.datePickerGoal.dayOfMonth}/${binding.datePickerGoal.month}/${binding.datePickerGoal.year%100}")

            if (validateGoalDetails()){
                val goalViewModel = ViewModelProvider(this)[GoalViewModel::class.java]
                val goal = Goal(
                    goal = binding.editTextGoalTittle.text.toString(),
                    date = goalViewModel.convertDate(
                        day = binding.datePickerGoal.dayOfMonth,
                        month = binding.datePickerGoal.month,
                        year = binding.datePickerGoal.year
                    ),
                    time = goalViewModel.convertTime(
                        hour = binding.timePickerGoal.hour,
                        minute = binding.timePickerGoal.minute
                    ),
                    duration = binding.numPickerSetTimer.value.toString() + " m"
                )

                listener?.onDataPassed(goal)
                // Dismiss the dialog fragment
                dismiss()
            }
        }
    }

    private fun validateGoalDetails() : Boolean{

        val dateTime = getCurrentDateTime()
        if(binding.editTextGoalTittle.text.toString().trim() == ""){
            binding.editTextGoalTittle.error = "Tittle not set"
            return false
        }else if (binding.numPickerSetTimer.value == 0){
            Toast.makeText(requireContext(), "Duration required", Toast.LENGTH_SHORT).show()
            return false
        }else if(binding.datePickerGoal.year == dateTime.year
            && binding.datePickerGoal.month == dateTime.monthValue - 1
            && binding.datePickerGoal.dayOfMonth == dateTime.dayOfMonth
            && binding.timePickerGoal.hour <= dateTime.hour){

            Log.d("time check", "here")
            if (binding.timePickerGoal.hour < dateTime.hour){
                Toast.makeText(requireContext(), "Time set before current time", Toast.LENGTH_SHORT).show()
                return false
            }else if(binding.timePickerGoal.hour == dateTime.hour
                && binding.timePickerGoal.minute <= dateTime.minute + 1){
                Toast.makeText(requireContext(), "Time set before current time", Toast.LENGTH_SHORT).show()
                return false
            }

        }
        return true
    }

    fun setListener(listener: DialogClickListener) {
        this.listener = listener
    }

    private fun getCurrentDate(): Date {
        return Calendar.getInstance().time
    }

    private fun getCurrentDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }
}