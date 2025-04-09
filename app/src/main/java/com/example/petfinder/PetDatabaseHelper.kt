package com.example.petfinder

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class PetDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "petfinder.db"
        private const val DATABASE_VERSION = 4

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
        private const val COLUMN_LOCATION = "location"
        private const val COLUMN_STATUS = "status"

        private const val CREATE_USERS_TABLE = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT UNIQUE NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_EMAIL TEXT,
                $COLUMN_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """

        private const val CREATE_PETS_TABLE = """
            CREATE TABLE $TABLE_PETS (
                $COLUMN_PET_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_BREED TEXT,
                $COLUMN_AGE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_REPORTER TEXT,
                $COLUMN_IMAGE TEXT,
                $COLUMN_DATE TEXT DEFAULT CURRENT_TIMESTAMP,
                $COLUMN_LOCATION TEXT,
                $COLUMN_STATUS TEXT DEFAULT 'perdido'
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_USERS_TABLE)
            db.execSQL(CREATE_PETS_TABLE)
            createDefaultAdminUser(db)
            insertSamplePets(db)
            Log.d("DB", "Database created successfully")
        } catch (e: Exception) {
            Log.e("DB", "Error creating database", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_PETS")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
            onCreate(db)
        } catch (e: Exception) {
            Log.e("DB", "Error upgrading database", e)
        }
    }

    private fun createDefaultAdminUser(db: SQLiteDatabase) {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, "admin")
            put(COLUMN_PASSWORD, "admin123")
            put(COLUMN_EMAIL, "admin@petfinder.com")
        }
        db.insert(TABLE_USERS, null, values)
    }

    private fun insertSamplePets(db: SQLiteDatabase) {
        db.execSQL("DELETE FROM $TABLE_PETS")

        val samplePets = listOf(
            PetData("Roky", "Chihuahua", "3 años", "Perdido en parque central", "Jose Hernandez", "dog1", "Parque Central"),
            PetData("Luna", "Golden Retriever", "2 años", "Perdida cerca del centro comercial", "María García", "dog2", "Centro Comercial"),
            PetData("Max", "Labrador", "4 años", "Desaparecido en zona norte", "Roberto Jiménez", "dog3", "Zona Norte"),
            PetData("Bella", "Bulldog Francés", "1 año", "Extraviada en parque de perros", "Ana Martínez", "dog4", "Parque Canino"),
            PetData("Michi", "Siamés", "1 año", "Gato perdido en colonia Centro", "Carlos Sánchez", "cat1", "Colonia Centro"),
            PetData("Luna", "Persa", "2 años", "Gata extraviada en Jardines", "Laura Fernández", "cat2", "Colonia Jardines"),
            PetData("Simba", "Mestizo", "3 años", "Gato naranja cerca del mercado", "Pedro Ramírez", "cat3", "Mercado Central"),
            PetData("Piolín", "Canario", "6 meses", "Pájaro escapado de jaula", "Sofía Castro", "bird1", "Avenida Principal"),
            PetData("Nemo", "Pez Payaso", "1 año", "Pez perdido (pecera rota)", "Luis Mendoza", "fish1", "Calle Flores"),
            PetData("Hoppy", "Conejo Enano", "8 meses", "Conejo escapado de jaula", "Elena Torres", "rabbit1", "Parque Infantil")
        )

        samplePets.forEach { pet ->
            ContentValues().apply {
                put(COLUMN_NAME, pet.name)
                put(COLUMN_BREED, pet.breed)
                put(COLUMN_AGE, pet.age)
                put(COLUMN_DESCRIPTION, pet.description)
                put(COLUMN_REPORTER, pet.reporter)
                put(COLUMN_IMAGE, pet.image)
                put(COLUMN_LOCATION, pet.location)
                put(COLUMN_STATUS, "perdido")
                db.insert(TABLE_PETS, null, this)
            }
        }
    }

    fun validateUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_USER_ID),
            "$COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?",
            arrayOf(username, password),
            null, null, null
        )
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }

    fun createUser(username: String, password: String, email: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_EMAIL, email)
        }
        return writableDatabase.insert(TABLE_USERS, null, values)
    }

    fun getAllPets(): List<Pet> {
        val pets = mutableListOf<Pet>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PETS,
            null, null, null, null, null,
            "$COLUMN_DATE DESC"
        )

        while (cursor.moveToNext()) {
            pets.add(Pet(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BREED)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REPORTER)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
            ).apply {
                location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION))
                status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            })
        }
        cursor.close()
        return pets
    }

    data class PetData(
        val name: String,
        val breed: String,
        val age: String,
        val description: String,
        val reporter: String,
        val image: String,
        val location: String
    )
}