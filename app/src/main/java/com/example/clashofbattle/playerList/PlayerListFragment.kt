package com.example.clashofbattle.playerList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.clashofbattle.R
import com.example.clashofbattle.ViewPlayersAdapter
import com.example.clashofbattle.combat.CombatFragment
import com.example.clashofbattle.databinding.FragmentListPlayerBinding
import com.example.clashofbattle.playerEdit.PlayerEditFragment
import com.example.clashofbattle.utils.getColor
import com.example.clashofbattle.utils.getNameId
import com.example.clashofbattle.utils.getPlayerJob
import com.example.democlashofbattle.utils.loadImage

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

        val adapter = ViewPlayersAdapter{ playerID ->
            val playerRes = bundleOf(CombatFragment.PLAYER_ID to playerID)
            findNavController().navigate(R.id.action_playerListFragment_to_combatFragment, playerRes)
        }
        binding?.rvPlayers?.adapter = adapter

        viewModel.mainPlayer.observe(viewLifecycleOwner) { player ->
            binding.mainPlayerName.text = player.name
            loadImage(binding.mainPlayerImage, player.imageUrl)
            binding.mainPlayerClass.setTextColor(getPlayerJob(player).getColor(requireContext()))
            binding.mainPlayerClass.setText(getPlayerJob(player).getNameId())
            binding.mainPlayer.setOnClickListener {
                val playerRes = bundleOf(PlayerEditFragment.PLAYER_ID to player.id)
                findNavController().navigate(R.id.action_playerListFragment_to_playerEditFragment, playerRes)
            }
        }

        viewModel.players?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}