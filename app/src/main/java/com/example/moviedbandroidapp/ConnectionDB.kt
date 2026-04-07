package com.example.moviedbandroidapp

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConnectionDB{
    companion object {
        private const val DB_HOST = "10.0.2.2"
        private const val DB_PORT = "3306"
        private const val DB_NAME = "moviedb"
        private const val DB_USERNAME = "root"
        private const val DB_PASSWORD = ""
        private const val DB_URL = "jdbc:mysql://$DB_HOST:$DB_PORT/$DB_NAME"


        fun getConnection(): Connection? {
            return try {
                Class.forName("com.mysql.cj.jdbc.Driver")
                DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                null
            } catch (e: SQLException) {
                e.printStackTrace()
                null
            }
        }
    }

    fun main(){
        val connection = ConnectionDB.getConnection()
        if (connection != null) {
            println("Connected to the database!")
        } else {
            println("Failed to connect to the database.")
        }
    }
}