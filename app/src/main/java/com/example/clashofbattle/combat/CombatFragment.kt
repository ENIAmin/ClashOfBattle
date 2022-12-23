package com.example.clashofbattle.combat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.clashofbattle.capabilities.SelectCapabilityActivity
import com.example.clashofbattle.databinding.FragmentCombatBinding
import com.example.clashofbattle.models.Player
import com.example.clashofbattle.playerEdit.PlayerEditViewModel
import com.example.clashofbattle.playerEdit.ViewCapabilitiesAdapter
import com.example.clashofbattle.utils.getColor
import com.example.clashofbattle.utils.getNameId
import com.example.democlashofbattle.utils.loadImage
import kotlinx.coroutines.launch

class CombatFragment : Fragment() {
    companion object {
        const val PLAYER_ID = "PLAYER_ID"
    }

    private var _binding: FragmentCombatBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel : CombatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CombatViewModel::class.java)
        _binding = FragmentCombatBinding.inflate(inflater, container, false)
        val id = arguments?.getLong(PLAYER_ID)
        id?.let{ viewModel.init(id) }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mainPlayer.observe(viewLifecycleOwner) { player ->
            loadImage(binding.playerOneImage, player.imageUrl)
            binding.playerOneName.text = player.name
            binding.FightButtonOne.setText(player.capability1.getNameId())
            binding.FightButtonTwo.setText(player.capability2.getNameId())
            binding.FightButtonThree.setText(player.capability3.getNameId())
        }

        viewModel.opponent.observe(viewLifecycleOwner) { opponent ->
            loadImage(binding.playerImageTwo, opponent.imageUrl)
            binding.playerNameTwo.text = opponent.name
        }

        viewModel.mainPlayerInfo.observe(viewLifecycleOwner) { mainPlayerInfo ->
            binding.simpleProgressBarOne.progress = mainPlayerInfo.pv
            binding.playerOnePv.text = "${mainPlayerInfo.pv} / 50"

            val c1 = mainPlayerInfo.remainingCapabilities.getOrNull(0)
            val c2 = mainPlayerInfo.remainingCapabilities.getOrNull(1)
            val c3 = mainPlayerInfo.remainingCapabilities.getOrNull(2)

            binding.FightButtonOne.isVisible = c1 != null
            binding.FightButtonTwo.isVisible = c2 != null
            binding.FightButtonThree.isVisible = c3 != null

            binding.FightButtonOne.setOnClickListener {
                c1?.let {
                    viewModel.attack(c1)
                }
            }

            binding.FightButtonTwo.setOnClickListener {
                c2?.let {
                    viewModel.attack(c2)
                }}

            binding.FightButtonThree.setOnClickListener() {
                c3?.let {
                    viewModel.attack(c3)
                }}

        }

        viewModel.opponentInfo.observe(viewLifecycleOwner) { opponentInfo ->
            binding.simpleProgressBarTwo.progress = opponentInfo.pv
            binding.playerTwoPv.text = "${opponentInfo.pv} / 50"
        }

        viewModel.winner.observe(viewLifecycleOwner) {
            binding.combatWinner.isVisible = it != null
            binding.combatWinner.text = "$it gagne !"
        }

        viewModel.roundCount.observe(viewLifecycleOwner) {
            binding.roundCount.text = "Tour n°$it"
        }

        binding.FightButtonNormal.setOnClickListener {
            viewModel.attack()
        }

        viewModel.lastPlayerResult.observe(viewLifecycleOwner) {
            binding.fightInfoOne.text = getTextForActionResult(
                requireContext(),
                viewModel.mainPlayer.value!!,
                it
            )
        }

        viewModel.lastOpponentResult.observe(viewLifecycleOwner) {
            binding.fightInfoTwo.text = getTextForActionResult(
                requireContext(),
                viewModel.opponent.value!!,
                it
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
