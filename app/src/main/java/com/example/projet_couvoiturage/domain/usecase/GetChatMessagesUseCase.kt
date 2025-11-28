package com.example.projet_couvoiturage.domain.usecase

import com.example.projet_couvoiturage.data.local.entity.ChatMessageEntity
import com.example.projet_couvoiturage.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(tripId: Long): Flow<List<ChatMessageEntity>> {
        return chatRepository.getMessagesForTrip(tripId)
    }
}
