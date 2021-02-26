package fr.julocorp.onbackpressed

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreStuffRepository(private val context: Context) : StuffRepository {
    override suspend fun saveStuff(stuff: String) {

        context.dataStore.edit { store ->
            val counter = store[intPreferencesKey(COUNTER_KEY)] ?: 0
            store[intPreferencesKey(COUNTER_KEY)] = counter + 1
            store[stringPreferencesKey(counter.toString())] = stuff
        }
    }

    override suspend fun getBunchOfStuff(): Flow<List<String>> = context.dataStore.data
        .map { store ->
            store.asMap().values.filterIsInstance<String>().toList() as List<String>
        }

    companion object {
        private const val COUNTER_KEY = "counter"
    }
}
