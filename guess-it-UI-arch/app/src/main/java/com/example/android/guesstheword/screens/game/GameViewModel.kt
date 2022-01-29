package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }

    // The current word
    private var _word = MutableLiveData<String>()
    val word : LiveData<String>
        get() = _word

    // The current score
    private var _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    private var _finishEvent = MutableLiveData<Boolean>()
    val finishEvent : LiveData<Boolean>
        get() = _finishEvent



    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private val timer : CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime : LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime, { time ->
        DateUtils.formatElapsedTime(time)
    })


    init {
        _currentTime.value = 60
        Log.i("viewmodel", "Inside ViewModel Create!")
        _score.value = 0
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onFinish() {
                _finishEvent.value = true
            }

            override fun onTick(p0: Long) {
                _currentTime.value = (_currentTime.value)?.minus(1)
            }
        }

        timer.start()
        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("viewmodel", "Inside ViewModel Clear!")
        timer.cancel()
    }


    /**
     * Resets the list of words and randomizes the order
     */

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            //gameFinished()
            // _finishEvent.value = true
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun finishComplete() {
        _finishEvent.value = false
    }
}