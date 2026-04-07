package com.example.moviedbandroidapp

import android.os.Bundle
import android.os.StrictMode
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.sql.Connection

class MainActivity : AppCompatActivity() {

    private lateinit var etMovieID: EditText
    private lateinit var etTitle: EditText
    private lateinit var etYear: EditText
    private lateinit var etDirector: EditText
    private lateinit var tvResult: TextView

    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnFind: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // ⚠️ Allow network on main thread (ONLY for testing)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // Bind UI
        etMovieID = findViewById(R.id.etMovieID)
        etTitle = findViewById(R.id.etTitle)
        etYear = findViewById(R.id.etYear)
        etDirector = findViewById(R.id.etDirector)
        tvResult = findViewById(R.id.tvResult)

        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnFind = findViewById(R.id.btnFind)
        btnDelete = findViewById(R.id.btnDelete)

        // Click listeners
        btnAdd.setOnClickListener { addMovie() }
        btnUpdate.setOnClickListener { updateMovie() }
        btnFind.setOnClickListener { findMovie() }
        btnDelete.setOnClickListener { deleteMovie() }
    }

    //  ADD
    private fun addMovie() {
        val movieID = etMovieID.text.toString().trim()
        val title = etTitle.text.toString().trim()
        val year = etYear.text.toString().trim()
        val director = etDirector.text.toString().trim()

        if (movieID.isEmpty() || title.isEmpty() || year.isEmpty() || director.isEmpty()) {
            tvResult.text = "ERROR: All fields must be filled."
            return
        }

        var conn: Connection? = null
        try {
            conn = ConnectionDB.getConnection()

            val sql = "INSERT INTO movies (movieID, title, year, director) VALUES (?, ?, ?, ?)"
            val stmt = conn!!.prepareStatement(sql)

            stmt.setInt(1, movieID.toInt())
            stmt.setString(2, title)
            stmt.setInt(3, year.toInt())
            stmt.setString(4, director)

            val rows = stmt.executeUpdate()
            tvResult.text = if (rows > 0) "Movie added successfully" else "Insert failed"

            stmt.close()
        } catch (e: Exception) {
            tvResult.text = "ERROR: ${e.message}"
        } finally {
            conn?.close()
        }
    }

    //  UPDATE
    private fun updateMovie() {
        val movieID = etMovieID.text.toString().trim()
        val title = etTitle.text.toString().trim()
        val year = etYear.text.toString().trim()
        val director = etDirector.text.toString().trim()

        if (movieID.isEmpty() || title.isEmpty() || year.isEmpty() || director.isEmpty()) {
            tvResult.text = "ERROR: All fields must be filled."
            return
        }

        var conn: Connection? = null
        try {
            conn = ConnectionDB.getConnection()

            val sql = "UPDATE movies SET title=?, year=?, director=? WHERE movieID=?"
            val stmt = conn!!.prepareStatement(sql)

            stmt.setString(1, title)
            stmt.setInt(2, year.toInt())
            stmt.setString(3, director)
            stmt.setInt(4, movieID.toInt())

            val rows = stmt.executeUpdate()
            tvResult.text = if (rows > 0) "Movie updated successfully" else "Update failed"

            stmt.close()
        } catch (e: Exception) {
            tvResult.text = "ERROR: ${e.message}"
        } finally {
            conn?.close()
        }
    }

    //  FIND
    private fun findMovie() {
        val movieID = etMovieID.text.toString().trim()

        if (movieID.isEmpty()) {
            tvResult.text = "ERROR: Enter Movie ID"
            return
        }

        var conn: Connection? = null
        try {
            conn = ConnectionDB.getConnection()

            val sql = "SELECT * FROM movies WHERE movieID=?"
            val stmt = conn!!.prepareStatement(sql)

            stmt.setInt(1, movieID.toInt())
            val rs = stmt.executeQuery()

            if (rs.next()) {
                etTitle.setText(rs.getString("title"))
                etYear.setText(rs.getInt("year").toString())
                etDirector.setText(rs.getString("director"))

                tvResult.text = "Movie Found"
            } else {
                tvResult.text = "No movie found"
            }

            rs.close()
            stmt.close()
        } catch (e: Exception) {
            tvResult.text = "ERROR: ${e.message}"
        } finally {
            conn?.close()
        }
    }

    // DELETE
    private fun deleteMovie() {
        val movieID = etMovieID.text.toString().trim()

        if (movieID.isEmpty()) {
            tvResult.text = "ERROR: Enter Movie ID"
            return
        }

        var conn: Connection? = null
        try {
            conn = ConnectionDB.getConnection()

            val sql = "DELETE FROM movies WHERE movieID=?"
            val stmt = conn!!.prepareStatement(sql)

            stmt.setInt(1, movieID.toInt())
            val rows = stmt.executeUpdate()

            tvResult.text = if (rows > 0) {
                clearFields()
                "Movie deleted successfully"
            } else {
                "No movie found"
            }

            stmt.close()
        } catch (e: Exception) {
            tvResult.text = "ERROR: ${e.message}"
        } finally {
            conn?.close()
        }
    }

    //  CLEAR INPUTS
    private fun clearFields() {
        etMovieID.text.clear()
        etTitle.text.clear()
        etYear.text.clear()
        etDirector.text.clear()
    }
}