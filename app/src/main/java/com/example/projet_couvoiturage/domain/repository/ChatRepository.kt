package com.example.projet_couvoiturage.domain.repository

import com.example.projet_couvoiturage.data.local.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getMessagesForTrip(tripId: Long): Flow<List<ChatMessageEntity>>
    suspend fun sendMessage(message: ChatMessageEntity)
}
