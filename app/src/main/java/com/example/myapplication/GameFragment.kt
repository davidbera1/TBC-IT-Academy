package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.FragmentGameBinding

private const val LEVEL= "level"

class GameFragment : Fragment() {

    private lateinit var adapter: GameAdapter
    private val list: MutableList<Item> = mutableListOf()
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    // lambda callbacks for MainActivity
    var onBackPressed: (() -> Unit)? = null
    private var level: LevelType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            level = it.getParcelable(LEVEL) ?: LevelType.GAME3x3 // default value 3x3
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListener()
        fillList(level)
        setUpRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpListener() {
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
            onBackPressed?.invoke()
        }
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        adapter = GameAdapter(list).apply {
            onGameResult = { result ->
                when(result) {
                    "X" -> binding.tvGameResult.text = getString(R.string.player_x_won)
                    "O" -> binding.tvGameResult.text = getString(R.string.player_o_won)
                    "draw" -> binding.tvGameResult.text = getString(R.string.draw)
                }
            }
            onButtonClicked = { (id, text) ->
                updateItem(id=id, text=text)
            }
        }
        recyclerView.adapter = adapter
        when(level) {
            LevelType.GAME3x3 -> recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            LevelType.GAME4x4 -> recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
            LevelType.GAME5x5 -> recyclerView.layoutManager = GridLayoutManager(requireContext(), 5)
            null -> recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun fillList(levelType: LevelType?) {
        var id = 1
        val size = when(levelType) {
            LevelType.GAME3x3 -> 9
            LevelType.GAME4x4 -> 16
            LevelType.GAME5x5 -> 25
            null -> 0
        }
        for (i in 0 until size) {
            list.add(Item(id=id, text=""))
            id++
        }
    }

    private fun updateItem(id: Int, text: String) {
        val item = list.find { it.id == id }
        item?.text = text
    }

    companion object {
        @JvmStatic
        fun newInstance(level: LevelType) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LEVEL, level)
                }
            }
    }
}