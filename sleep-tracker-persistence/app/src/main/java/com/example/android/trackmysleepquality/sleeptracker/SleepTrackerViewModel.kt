/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

        private var tonight = MutableLiveData<SleepNight?>()
        private val nights = database.getAllSleepNights()
        val nightsString = Transformations.map(nights) {
                formatNights(it, application.resources)
        }

        val startVisible = Transformations.map(tonight) {
                (null == it)
        }

        val stopVisible = Transformations.map(tonight) {
                (null != it)
        }

        val clearVisible = Transformations.map(nights) {
                it.size != 0
        }

        val _clearEvent = MutableLiveData<Boolean>()
        val clearEvent: LiveData<Boolean>
                get() = _clearEvent
        var _stopped = MutableLiveData<SleepNight?>()
        val stopped: LiveData<SleepNight?>
                get() = _stopped
        init {
                initializeTonight()
        }

        fun initializeTonight() {
                viewModelScope.launch {
                        tonight.value = getTonightDb()
                        Log.i("sleeptrackerVM", tonight.value.toString())
                }
        }

        private suspend fun getTonightDb(): SleepNight?{
                return withContext(Dispatchers.IO){
                        var night = database.getTonight()
                        if(night?.endTimeMilli != night?.startTimeMilli) {
                                night = null
                        }
                        night
                }
        }

        fun onStartTracking() {
                viewModelScope.launch {
                        val newNight = SleepNight()
                        insertDb(newNight)
                        tonight.value = getTonightDb()
                }
        }

        private suspend fun insertDb(sleepNight: SleepNight) {

                withContext(Dispatchers.IO) {
                        database.insert(sleepNight)
                }
        }

        fun onStopTracking() {
                viewModelScope.launch {
                        val oldNight = tonight.value ?:return@launch
                        oldNight.endTimeMilli = System.currentTimeMillis()
                        update(oldNight)
                        _stopped.value = oldNight
                }
        }

        private suspend fun update(sleepNight: SleepNight) {
                withContext(Dispatchers.IO) {
                        database.update(sleepNight)
                }
        }

        fun onClear() {
                viewModelScope.launch {
                        clear()
                        tonight.value = null
                        _clearEvent.value = true
                }
        }

        private suspend fun clear(){
                withContext(Dispatchers.IO) {
                        database.clear()
                }
        }

        fun doneNavigating() {
                _stopped.value = null
        }

        fun doneSnackBar() {
                _clearEvent.value = false
        }



}

