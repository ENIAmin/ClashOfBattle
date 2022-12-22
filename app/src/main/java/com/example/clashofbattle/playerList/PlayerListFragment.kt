package com.example.clashofbattle.playerList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.clashofbattle.R
import com.example.clashofbattle.ViewPlayersAdaptater
import com.example.clashofbattle.databinding.FragmentListPlayerBinding
import com.example.clashofbattle.playerEdit.PlayerEditFragment

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayerListFragment : Fragment() {

    private var _binding: FragmentListPlayerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListPlayerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(PlayerListViewModel::class.java)

        val adapter = ViewPlayersAdaptater{ playerID ->
            val playerRes = bundleOf(PlayerEditFragment.PLAYER_ID to playerID)
//          ECRAN DE COMBAT  findNavController().navigate(R.id.action_playerListFragment_to_playerEditFragment, playerRes)
        }
        binding?.rvPlayers?.adapter = adapter


        viewModel.players?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        println("Main Player: ${viewModel.mainPlayer?.value}")
//        for(player in viewModel.players){
//            println("Player: $player")
//        }
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_playerListFragment_to_playerEditFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}