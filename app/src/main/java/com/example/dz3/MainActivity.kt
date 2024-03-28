package com.example.dz3

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), HabitAdapter.OnItemClickListener {

    private var habitList = ArrayList<Habit>().toMutableList()
    private val adapter = HabitAdapter(habitList, this)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { _ ->
            val intent = Intent(this, CreateOrEditHabit::class.java)
            habitActivityForResult.launch(intent)
        }
    }

    private val habitActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val habit: Habit = result.data?.extras?.get("habit") as Habit
                val index = result.data!!.extras?.get("position") as Int
                val newHabit = Habit(
                    R.drawable.circle_green_24,
                    habit.title,
                    habit.description,
                    habit.priority,
                    habit.type,
                    habit.repeat,
                    habit.days
                )
                if (result.data?.getBooleanExtra("edit", false) == true) {
                    habitList.set(index, newHabit)
                    adapter.notifyItemChanged(index)
                } else {
                    habitList.add(index, newHabit)
                    adapter.notifyItemInserted(index)
                }
            }
        }

    override fun onItemClick(position: Int) {
        val clickedHabit = habitList[position]
        val intent = Intent(this, CreateOrEditHabit::class.java)
        intent.putExtra("edit", true)
        intent.putExtra("position", position)
        intent.putExtra("habit", clickedHabit)
        habitActivityForResult.launch(intent)
    }
}