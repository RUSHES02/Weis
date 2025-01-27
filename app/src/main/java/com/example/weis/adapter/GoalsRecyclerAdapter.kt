package com.example.weis.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weis.R
import com.example.weis.modals.Goal
import com.example.weis.utils.DateTimeEpoch
import java.time.LocalDateTime

class GoalsRecyclerAdapter : RecyclerView.Adapter<GoalsRecyclerAdapter.GoalsHolder>() {

    private var lastActive : GoalsHolder? = null
    private var lastActivePos : Int? = null

    private val differCallBack = object: DiffUtil.ItemCallback<Goal>() {
        override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem == newItem
        }
    }


    private val differ = AsyncListDiffer(this,differCallBack)

    fun saveData( dataResponse: List<Goal>){
        differ.submitList(dataResponse)
        Log.d("in adapter", "${differ.currentList}")

    }

    inner class GoalsHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        private val textGoalTitle : TextView = itemView.findViewById(R.id.textGoalTittle)
        private val textGoalDur : TextView = itemView.findViewById(R.id.textGoalDur)
        private var btnStart : Button = itemView.findViewById(R.id.btnStart)
        private val textGoalDateTime : TextView = itemView.findViewById(R.id.textGoalDatetime)

        fun bind (goal : Goal){
            textGoalTitle.text = goal.goal
            textGoalDur.text = goal.duration.toString() + " m"
            textGoalDateTime.text = "${goal.date}   ${goal.time}"
            btnStart.visibility = View.GONE

            Log.d("adapter", "${goal}    ")
            val dateTime = getCurrentDateTime()
            val currentTimestamp = DateTimeEpoch
                .dateToEpochTime("${dateTime.dayOfMonth} " +
                        "${dateTime.monthValue - 1} " +
                        "${dateTime.year} " +
                        "${dateTime.hour} " +
                        "${dateTime.minute} 00", "dd MM yyy HH mm ss")
            if(goal.timestamp!! <= currentTimestamp){
                btnStart.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_button_enabled)
            }else{
                btnStart.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_button_disable)
                btnStart.isEnabled = false
            }
            btnStart.setOnClickListener{
                Log.d("Start Button","clicked")
                onStartClick?.invoke(goal)
            }
            itemView.setOnClickListener{
                if (goal.open){
                    btnStart.visibility = View.GONE
                    lastActive = null
                    lastActivePos = null
                }else{
                    btnStart.visibility = View.VISIBLE
                    lastActive?.btnStart?.visibility = View.GONE
                    if(lastActivePos != null){
                        differ.currentList[lastActivePos!!].open = false
                    }
                    lastActive = GoalsHolder(itemView)
                    lastActivePos = adapterPosition
                }
                differ.currentList[position].open = !goal.open
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsHolder {
        return GoalsHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_goal, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: GoalsHolder, position: Int) {
        val goal = differ.currentList[position]
        Log.d("in adapter", "yes")
        holder.bind(goal)
    }

    var onItemClick :((Goal) -> Unit)? = null
    var onStartClick :((Goal) -> Unit)? = null

    private fun getCurrentDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }
}