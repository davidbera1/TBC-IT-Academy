package com.example.learnandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.databinding.FragmentChatBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ChatFragment : Fragment() {

    private var messageList: MutableList<MessageItem> = mutableListOf()
    private lateinit var adapter: MessageAdapter
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListener()

        val recyclerView = binding.recyclerView
        adapter = MessageAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpListener() {
        binding.btnSendMessage.setOnClickListener {
            val message = binding.etMessage.text.toString()
            val (time, day) = getCurrentDate()
            val date = "$day, $time"
            if (message.isNotEmpty()) {
                messageList.add(0, MessageItem(message=message, sendDate=date))
                adapter.submitList(messageList.toMutableList())
                // clear input field after sending a message
                binding.etMessage.setText("")
            }
        }
    }

    private fun getCurrentDate(): Pair<String, String> {
        val currentTime = LocalTime.now()
        val formatType = DateTimeFormatter.ofPattern("hh:mma")
        val result = currentTime.format(formatType).lowercase()
        return Pair(result, "Today")
    }
}