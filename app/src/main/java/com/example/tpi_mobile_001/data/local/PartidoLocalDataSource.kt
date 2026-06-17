package com.example.tpi_mobile_001.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tpi_mobile_001.models.Partido
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "partidos_prefs")

class PartidoLocalDataSource(private val context: Context) {

    private val KEY = stringPreferencesKey("partidos_json")
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun guardarPartidos(lista: List<Partido>) {
        val texto = json.encodeToString(lista)
        context.dataStore.edit { prefs ->
            prefs[KEY] = texto
        }
    }

    suspend fun obtenerPartidos(): List<Partido> {
        val prefs = context.dataStore.data.first()
        val texto = prefs[KEY] ?: return emptyList()
        return json.decodeFromString<List<Partido>>(texto)
    }
}