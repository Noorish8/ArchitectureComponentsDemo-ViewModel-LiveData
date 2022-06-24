package com.example.architecturecomponentsdemo.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.architecturecomponentsdemo.R
import com.example.architecturecomponentsdemo.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class GameFragment : Fragment() {

    private lateinit var binding:FragmentGameBinding
    private val viewModel: GameViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_game, container, false)
        binding=FragmentGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the viewModel for data binding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.gameViewModel=viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS

        binding.lifecycleOwner=viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }

    }
    private fun onSubmitWord(){
    val playWord = binding.textViewInstructions.text.toString()
   if (viewModel.isUserWordCorrect(playWord)){
       setErrorTextField(false)
if (!viewModel.nextWord()) {
    showFinalScoreDialog()
}
}else{
    setErrorTextField(true)
   }
    }
    /*
    * Re-initializes the data in the ViewModel and updates the views with the new data, to
    * restart the game.
    */
   private fun restartGame(){
   viewModel.reinitializeData()
        setErrorTextField(false)
   }
    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }


    private fun setErrorTextField(error:Boolean){
if (error){
binding.textField.isErrorEnabled = true
binding.textField.error = getString(R.string.try_again)
}
}
    private fun onSkipWord(){
if (viewModel.nextWord()){
setErrorTextField(false)
}else{
showFinalScoreDialog()
}
    }

    private fun showFinalScoreDialog(){
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(getString(R.string.congratulations))
        .setMessage(getString(R.string.you_scored ,viewModel.score.value))
        .setCancelable(false)
        .setNegativeButton(getString(R.string.exit)){_, _ ->
            exitGame()
        }
        .setPositiveButton(getString(R.string.play_again)){ _,_ ->
            restartGame()
        }
    }


}