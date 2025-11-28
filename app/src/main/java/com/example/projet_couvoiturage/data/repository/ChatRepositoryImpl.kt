package com.example.projet_couvoiturage.data.repository

import com.example.projet_couvoiturage.data.local.dao.ChatDao
import com.example.projet_couvoiturage.data.local.entity.ChatMessageEntity
import com.example.projet_couvoiturage.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao
) : ChatRepository {
    override fun getMessagesForTrip(tripId: Long): Flow<List<ChatMessageEntity>> {
        return chatDao.getMessagesForTrip(tripId)
    }

    override suspend fun sendMessage(message: ChatMessageEntity) {
        chatDao.insertMessage(message)
    }
}
