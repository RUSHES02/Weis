package com.example.weis.adapter

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
    }

    inner class GoalsHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val textGoalTitle : TextView = itemView.findViewById(R.id.textGoalTittle)
        val textGoalDur : TextView = itemView.findViewById(R.id.textGoalDur)
        var btnStart : Button = itemView.findViewById(R.id.btnStart)
        val textGoalDateTime : TextView = itemView.findViewById(R.id.textGoalDatetime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsHolder {
        return GoalsHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_goal, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: GoalsHolder, position: Int) {
        val goal = differ.currentList[position]
        holder.textGoalTitle.text = goal.goal
        holder.textGoalDur.text = goal.duration
        holder.textGoalDateTime.text = "${goal.date}   ${goal.time}"
        holder.btnStart.visibility = View.GONE
        holder.btnStart.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_text_box)
        holder.itemView.setOnClickListener{
            if (goal.open){
                holder.btnStart.visibility = View.GONE
                lastActive = null
                lastActivePos = null
            }else{
                holder.btnStart.visibility = View.VISIBLE
                lastActive?.btnStart?.visibility = View.GONE
                if(lastActivePos != null){
                    differ.currentList[lastActivePos!!].open = false
                }
                lastActive = holder
                lastActivePos = holder.adapterPosition
            }
            differ.currentList[position].open = !goal.open
        }
    }

    var onItemClick :((Goal) -> Unit)? = null
    var onStartClick :((Goal) -> Unit)? = null
}