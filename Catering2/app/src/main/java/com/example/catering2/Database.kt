package com.example.catering2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object LoginSchema {
    object LoginEntry : BaseColumns {
        const val TABLE_NAME = "logreg"
        const val COLUMN_NAME_LOGIN = "login"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_EMAIL = "email"
    }
}

private const val logreg_create = "CREATE TABLE ${LoginSchema.LoginEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
        "${LoginSchema.LoginEntry.COLUMN_NAME_LOGIN} TEXT," +
        "${LoginSchema.LoginEntry.COLUMN_NAME_PASSWORD} TEXT," +
        "${LoginSchema.LoginEntry.COLUMN_NAME_EMAIL} TEXT)"

private const val logreg_delete = "DROP TABLE IF EXISTS ${LoginSchema.LoginEntry.TABLE_NAME}"

class Database(context: Context) : SQLiteOpenHelper(context, "AppBase.db", null,2) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(logreg_create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(logreg_delete)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    class PizzaDatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            private const val DATABASE_NAME = "pizza_database"
            private const val DATABASE_VERSION = 1

            private const val TABLE_PIZZA = "pizza"
            private const val COLUMN_ID = "id"
            const val COLUMN_NAME = "name"
            const val COLUMN_PRICE = "price"
            const val COLUMN_ADDRESS = "adres"
            const val COLUMN_NUMBER = "number"


            private const val CREATE_TABLE_PIZZA = "CREATE TABLE $TABLE_PIZZA (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_NAME TEXT," +
                    "$COLUMN_PRICE INTEGER," +
                    "$COLUMN_ADDRESS TEXT," +
                    "$COLUMN_NUMBER TEXT)"

        }

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_TABLE_PIZZA)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_PIZZA")
            onCreate(db)
        }

        fun addPizza(name: String, price: Int, address: String, number: String) {
            val values = ContentValues()
            values.put(COLUMN_NAME, name)
            values.put(COLUMN_PRICE, price)
            values.put(COLUMN_ADDRESS, address) // Dodajemy kolumnę adresu zamówienia
            values.put(COLUMN_NUMBER, number)

            val db = writableDatabase
            db.insert(TABLE_PIZZA, null, values)
            db.close()
        }

        fun getData(): Cursor {
            val db = this.readableDatabase
            return db.rawQuery("SELECT * FROM $TABLE_PIZZA", null)
        }

        fun clearPizzaTable() {
            val db = this.writableDatabase
            db.execSQL("DELETE FROM $TABLE_PIZZA")
        }
    }
    class AdminDatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object {
            private const val DATABASE_NAME = "admin_database"
            private const val DATABASE_VERSION = 2

            const val TABLE_ADMIN = "admin"
            const val COLUMN_LOGIN = "login"
            const val COLUMN_PASSWORD = "password"
        }

        override fun onCreate(db: SQLiteDatabase) {
            val createTableStatement = """
        CREATE TABLE $TABLE_ADMIN (
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_LOGIN TEXT,
            $COLUMN_PASSWORD TEXT
        )
    """.trimIndent()

            db.execSQL(createTableStatement)

            // Dodaj administratora
            val adminLogin = "maciek123"
            val adminPassword = ("domek123")  // Hasło powinno być zahaszowane
            val insertAdminStatement = """
        INSERT INTO $TABLE_ADMIN (
            $COLUMN_LOGIN,
            $COLUMN_PASSWORD
        ) VALUES (
            '$adminLogin',
            '$adminPassword'
        )
    """.trimIndent()

            db.execSQL(insertAdminStatement)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_ADMIN")
            onCreate(db)
        }
    }
}