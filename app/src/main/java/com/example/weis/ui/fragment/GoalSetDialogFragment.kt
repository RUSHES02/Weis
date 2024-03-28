package com.example.weis.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.weis.databinding.FragmentGoalSetDialogBinding
import com.example.weis.modals.Goal
import com.example.weis.utils.DialogClickListener
import com.example.weis.utils.ScreenUtils
import com.example.weis.viewModel.GoalViewModel

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

        binding.btnSetGoal.setOnClickListener{
            val goalViewModel = ViewModelProvider(this)[GoalViewModel::class.java]
            val goal = Goal(
                goal = binding.editTextGoalTittle.text.toString(),
                date = goalViewModel.convertDate(
                    day = binding.datePickerGoal.dayOfMonth,
                    month = binding.datePickerGoal.month,
                    year =  binding.datePickerGoal.year),
                time = goalViewModel.convertTime(
                    hour = binding.timePickerGoal.hour,
                    minute = binding.timePickerGoal.minute),
                duration = binding.numPickerSetTimer.value.toString() + " m"
            )

            listener?.onDataPassed(goal)
            // Dismiss the dialog fragment
            dismiss()
        }
    }

    fun setListener(listener: DialogClickListener) {
        this.listener = listener
    }
}