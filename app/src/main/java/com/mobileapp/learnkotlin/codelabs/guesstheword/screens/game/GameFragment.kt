package com.mobileapp.learnkotlin.codelabs.guesstheword.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.mobileapp.learnkotlin.R
import com.mobileapp.learnkotlin.databinding.FragmentGameGuessTheWordBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameGuessTheWordBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_guess_the_word,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        viewModel.score.observe(
            viewLifecycleOwner,
            Observer { newScore -> binding.scoreText.text = newScore.toString() })
        viewModel.word.observe(
            viewLifecycleOwner,
            Observer { newWord -> binding.wordText.text = newWord })
        viewModel.eventGameFinish.observe(
            viewLifecycleOwner,
            Observer { hasFinished -> if (hasFinished) endGame() })
        binding.skipButton.setOnClickListener { onSkip() }
        binding.correctButton.setOnClickListener { onCorrect() }
        binding.endGameButton.setOnClickListener { endGame() }
        return binding.root
    }

    private fun onSkip() {
        viewModel.onSkip()
    }

    private fun onCorrect() {
        viewModel.onCorrect()
    }

    private fun endGame() {
        Toast.makeText(activity, "Game has ended!!!", Toast.LENGTH_LONG).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value!!
        findNavController().navigate(action)
        viewModel.onEndGameComplete()
    }
}
