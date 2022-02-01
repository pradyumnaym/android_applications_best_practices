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

package com.example.android.trackmysleepquality.sleepquality

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SleepQualityViewModel(
    private val sleepDatabase: SleepDatabaseDao,
    private val sleepNightKey: Long = 0L) : ViewModel() {
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun onSetSleepQuality(quality: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tonight = sleepDatabase.get(sleepNightKey) ?: return@withContext
                tonight.sleepQuality = quality
                sleepDatabase.update(tonight)
            }
            _navigateToSleepTracker.value = true
        }
    }

    fun doneNavigating(){
        _navigateToSleepTracker.value = false
    }
}
