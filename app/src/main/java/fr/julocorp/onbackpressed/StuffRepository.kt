package fr.julocorp.onbackpressed

import kotlinx.coroutines.flow.Flow

interface StuffRepository {
    suspend fun saveStuff(stuff: String)

    suspend fun  getBunchOfStuff(): Flow<List<String>>
}