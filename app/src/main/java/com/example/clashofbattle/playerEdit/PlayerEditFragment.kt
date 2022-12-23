package com.example.clashofbattle.playerEdit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.clashofbattle.R
import com.example.clashofbattle.capabilities.SelectCapabilityActivity
import com.example.clashofbattle.databinding.FragmentEditPlayerBinding
import com.example.democlashofbattle.utils.loadImage
import kotlinx.coroutines.launch
import playerName

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PlayerEditFragment : Fragment() {

    companion object {
        const val PLAYER_ID = "PLAYER_ID"
    }

    private var _binding: FragmentEditPlayerBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel : PlayerEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {viewModel = ViewModelProvider(this).get(PlayerEditViewModel::class.java)
        _binding = FragmentEditPlayerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewCapabilitiesAdapter{ index ->
            val intent = SelectCapabilityActivity.newIntent(requireContext(), index)
            activityLauncher.launch(intent)
        }

        binding.rvCapabilities.adapter = adapter

        val id = arguments?.getLong(PLAYER_ID)
        id?.let {
            //lifecycleScope.launch{
                //val player = viewModel.getPlayerById(id)
                viewModel.player.observe(viewLifecycleOwner) {
                    loadImage(binding.editPlayerImage, it.imageUrl)
                    binding.editPlayerName.text = it.name
                    binding.editPlayerImageUrl.setText(it.imageUrl)
                    adapter.submitList(it.capabilities)
                }
            //}
        }
        binding.btnValidate.setOnClickListener {
            lifecycleScope.launch {
                viewModel.player.value!!.imageUrl = binding.editPlayerImageUrl.text.toString()
                viewModel.updatePlayer(playerName, viewModel.player.value!!)
                findNavController().popBackStack()
            }
        }

    }

    private val activityLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.let { intent ->
            val pair = SelectCapabilityActivity.extractResultData(intent)
            viewModel.updateCapability(pair.first, pair.second!!)
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}