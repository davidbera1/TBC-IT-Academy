package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.adapter.ChatAdapter
import com.example.learnandroid.databinding.FragmentChatsBinding
import com.example.learnandroid.viewmodel.ChatsViewModel
import kotlinx.coroutines.launch

class ChatsFragment : BaseFragment<FragmentChatsBinding>(FragmentChatsBinding::inflate) {

    private val viewModel: ChatsViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun setUpListeners() {
        binding.imgBtnSearch.setOnClickListener {
            // search
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatAdapter = ChatAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }

        lifecycleScope.launch {
            viewModel.chatsFlow.collect { chats ->
                chatAdapter.submitList(chats)
            }
        }
    }
}