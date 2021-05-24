package ua.kpi.comsys.ip8404.ui.movie

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import ua.kpi.comsys.ip8404.R

class AddMovieActivity (): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addmovie)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val Title: String = findViewById<AppCompatEditText>(R.id.titleEdit).text.toString()
            val Type: String = findViewById<AppCompatEditText>(R.id.typeEdit).text.toString()
            val Year: String = findViewById<AppCompatEditText>(R.id.yearEdit).text.toString()

        val returnIntent = Intent()
        returnIntent.putExtra("Title", Title)
        returnIntent.putExtra("Type", Type)
        returnIntent.putExtra("Year", Year)
        setResult(Activity.RESULT_OK, returnIntent);
        finish()
        }
    }
}