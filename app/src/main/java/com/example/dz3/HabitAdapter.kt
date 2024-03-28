package com.example.dz3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitAdapter(
    private val habitList: List<Habit>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val habitView = LayoutInflater.from(parent.context).inflate(
            R.layout.habit_item,
            parent, false
        )
        return HabitViewHolder(habitView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = habitList[position]

        holder.colorView.setImageResource(currentHabit.color)
        holder.titleView.text = currentHabit.title
        holder.descriptionView.text = currentHabit.description
        holder.priorityView.text = currentHabit.priority.toString()
        holder.typeView.text = currentHabit.type
        holder.periodView.text = "${currentHabit.repeat} раз в ${currentHabit.days} дней"
    }

    override fun getItemCount() = habitList.size

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val colorView: ImageView = itemView.findViewById(R.id.color_image_view)
        val titleView: TextView = itemView.findViewById(R.id.title_text_view)
        val descriptionView: TextView = itemView.findViewById(R.id.description_text_view)
        val priorityView: TextView = itemView.findViewById(R.id.priority_text_view)
        val typeView: TextView = itemView.findViewById(R.id.type_text_view)
        val periodView: TextView = itemView.findViewById(R.id.period_text_view)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}