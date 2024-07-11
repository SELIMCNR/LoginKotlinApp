package com.example.ozetuygulama



import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_NUMBER = "number"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USERNAME TEXT,"
                + "$COLUMN_EMAIL TEXT,"
                + "$COLUMN_NUMBER TEXT,"
                + "$COLUMN_PASSWORD TEXT" + ")")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USERNAME, user.username)
        contentValues.put(COLUMN_EMAIL, user.email)
        contentValues.put(COLUMN_NUMBER, user.number)
        contentValues.put(COLUMN_PASSWORD, user.password)

        val success = db.insert(TABLE_USERS, null, contentValues)
        db.close()
        return success
    }

    fun getUser(username: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_USERS, arrayOf(COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_NUMBER, COLUMN_PASSWORD),  "$COLUMN_USERNAME=? OR $COLUMN_EMAIL=?", arrayOf(username),
              null, null, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val user = User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                )
                cursor.close()
                return user
            }
            cursor.close()
        }
        return null
    }
}
