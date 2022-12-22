package com.example.clashofbattle.playerEdit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.clashofbattle.R
import com.example.clashofbattle.databinding.FragmentEditPlayerBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PlayerEditFragment : Fragment() {

    companion object {
        const val PLAYER_ID = "PLAYER_ID"
    }

    private var _binding: FragmentEditPlayerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditPlayerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_playerEditFragment_to_playerListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}