package com.example.dz3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children


class CreateOrEditHabit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit)
        if (this.intent.getBooleanExtra("edit", false)) {
            findViewById<TextView>(R.id.action_title).setText("Изменение привычки")
            findViewById<Button>(R.id.button).setText("Изменить")
            var habit: Habit = this.intent.extras?.get("habit") as Habit
//            findViewById<ImageView>(R.id.color_image_view).setImageResource(habit.color)
            findViewById<EditText>(R.id.title_input).setText(habit.title)
            findViewById<EditText>(R.id.description_input).setText(habit.description)
            findViewById<EditText>(R.id.repeat_input).setText(habit.repeat.toString())
            findViewById<EditText>(R.id.days_input).setText(habit.days.toString())

            fun getIndex(spinner: Spinner, myString: String): Int {
                var index = 0;
                for (i in (0..<spinner.count)) {
                    if (spinner.getItemAtPosition(i).equals(myString)) {
                        index = i;
                    }
                }
                return index;
            }

            var spinner = findViewById<Spinner>(R.id.priority_selector)
            spinner.setSelection(getIndex(spinner, habit.priority));

            var radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
            for (radioButton in radioGroup.children) {
                var rb = radioButton as RadioButton
                radioButton.isChecked = rb.text == habit.type
            }
        }
    }

    fun saveHabit(view: View) {
        var color: Int = 1
        var title: String = findViewById<EditText>(R.id.title_input).text.toString()
        var description: String = findViewById<EditText>(R.id.description_input).text.toString()
        var priority: String = findViewById<Spinner>(R.id.priority_selector).selectedItem.toString()
        var type_val =
            findViewById<RadioButton>(findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId).text.toString()
        var repeat: Int = findViewById<EditText>(R.id.repeat_input).text.toString().toInt()
        var days: Int = findViewById<EditText>(R.id.days_input).text.toString().toInt()


        val habit: Habit = Habit(color, title, description, priority, type_val, repeat, days)
        val intent: Intent = Intent();
        intent.putExtra("edit", this.intent.getBooleanExtra("edit", false))
        intent.putExtra("position", this.intent.getIntExtra("position", 0))
        intent.putExtra("habit", habit as java.io.Serializable)
        setResult(RESULT_OK, intent);
        finish();
    }
}