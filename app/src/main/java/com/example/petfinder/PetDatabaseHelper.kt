package com.example.petfinder

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class PetDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Configuración de la base de datos
        private const val DATABASE_NAME = "petfinder.db"
        private const val DATABASE_VERSION = 2

        // Tabla de usuarios
        private const val TABLE_USERS = "users"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_CREATED_AT = "created_at"

        // Tabla de mascotas
        private const val TABLE_PETS = "pets"
        private const val COLUMN_PET_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_BREED = "breed"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_REPORTER = "reporter_name"
        private const val COLUMN_IMAGE = "image_url"
        private const val COLUMN_DATE = "date"

        // Sentencias SQL
        private const val CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT UNIQUE NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_EMAIL TEXT,
                $COLUMN_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """

        private const val CREATE_PETS_TABLE = """
            CREATE TABLE IF NOT EXISTS $TABLE_PETS (
                $COLUMN_PET_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_BREED TEXT,
                $COLUMN_AGE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_REPORTER TEXT,
                $COLUMN_IMAGE TEXT,
                $COLUMN_DATE TEXT DEFAULT CURRENT_TIMESTAMP
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            // Crear ambas tablas
            db.execSQL(CREATE_USERS_TABLE)
            db.execSQL(CREATE_PETS_TABLE)

            // Insertar datos iniciales
            createDefaultAdminUser(db)
            insertSamplePets(db)

            Log.d("PetDatabaseHelper", "Database created successfully")
        } catch (e: Exception) {
            Log.e("PetDatabaseHelper", "Error creating database", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_PETS")
            onCreate(db)
            Log.d("PetDatabaseHelper", "Database upgraded successfully")
        } catch (e: Exception) {
            Log.e("PetDatabaseHelper", "Error upgrading database", e)
        }
    }

    private fun createDefaultAdminUser(db: SQLiteDatabase) {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, "Admin")
            put(COLUMN_PASSWORD, "Admin")
            put(COLUMN_EMAIL, "admin@petfinder.com")
        }

        try {
            val id = db.insert(TABLE_USERS, null, values)
            if (id != -1L) {
                Log.d("PetDatabaseHelper", "Default admin user created")
            }
        } catch (e: Exception) {
            Log.e("PetDatabaseHelper", "Error creating admin user", e)
        }
    }

    private fun insertSamplePets(db: SQLiteDatabase) {
        try {
            db.execSQL("DELETE FROM $TABLE_PETS")

            val samplePets = listOf(
                Pet(0, "Roky", "Chihuahua", "3 años",
                    "Mascota perdida en el parque central",
                    "Jose Angel Hernandez Santiago", "dog1"),
                Pet(0, "Luna", "Golden Retriever", "2 años",
                    "Perdida cerca del centro comercial",
                    "María García López", "dog2"),
                Pet(0, "Michi", "Siamés", "1 año",
                    "Gato perdido en la colonia Centro",
                    "Carlos Sánchez", "cat1")
            )

            samplePets.forEach { pet ->
                val values = ContentValues().apply {
                    put(COLUMN_NAME, pet.name)
                    put(COLUMN_BREED, pet.breed)
                    put(COLUMN_AGE, pet.age)
                    put(COLUMN_DESCRIPTION, pet.description)
                    put(COLUMN_REPORTER, pet.reporterName)
                    put(COLUMN_IMAGE, pet.imageUrl)
                }
                db.insert(TABLE_PETS, null, values)
            }
            Log.d("DatabaseHelper", "Sample pets inserted successfully")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting sample pets", e)
        }
    }

    // Métodos para usuarios
    fun validateUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_USER_ID),
            "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(username, password),
            null, null, null
        )

        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }

    // Métodos para mascotas
    fun getAllPets(): List<Pet> {
        val pets = mutableListOf<Pet>()
        val db = readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.query(
                TABLE_PETS,
                null, null, null, null, null,
                "$COLUMN_DATE DESC"
            )

            while (cursor?.moveToNext() == true) {
                pets.add(Pet(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BREED)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REPORTER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                ))
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error getting pets", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return pets
    }

    fun addPet(pet: Pet): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, pet.name)
            put(COLUMN_BREED, pet.breed)
            put(COLUMN_AGE, pet.age)
            put(COLUMN_DESCRIPTION, pet.description)
            put(COLUMN_REPORTER, pet.reporterName)
            put(COLUMN_IMAGE, pet.imageUrl)
        }

        return try {
            db.insert(TABLE_PETS, null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting pet", e)
            -1L
        } finally {
            db.close()
        }
    }
}