package com.example.projet_couvoiturage.ui.employee.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projet_couvoiturage.data.local.entity.ChatMessageEntity
import com.example.projet_couvoiturage.domain.usecase.GetChatMessagesUseCase
import com.example.projet_couvoiturage.domain.usecase.SendChatMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase
) : ViewModel() {

    fun getMessages(tripId: Long): LiveData<List<ChatMessageEntity>> {
        return getChatMessagesUseCase(tripId).asLiveData()
    }

    fun sendMessage(tripId: Long, senderId: Long, content: String) {
        viewModelScope.launch {
            val message = ChatMessageEntity(
                tripId = tripId,
                senderId = senderId,
                content = content,
                timestamp = System.currentTimeMillis()
            )
            sendChatMessageUseCase(message)
        }
    }
}
