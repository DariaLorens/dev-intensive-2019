package ru.skillbranch.devintensive.data.managers

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.utils.DataGenerator

object CacheManager {
    private val users = mutableLiveData(DataGenerator.stabUsers)
    private val chats = mutableLiveData(DataGenerator.stabChats)

    fun findUsersByIds(ids: List<String>) : List<User> {
        return users.value!!.filter { ids.contains(it.id) }
    }

    fun loadChats() : MutableLiveData<List<Chat>> {
        return chats
    }

    fun insertChat(chat: Chat) {
        val copy = chats.value!!.toMutableList()
        copy.add(chat)
        chats.value = copy
    }

    fun nextChatId() : String {
        return "${chats.value!!.size}"
    }
}