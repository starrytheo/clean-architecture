package net.starry.cleanarch.presentation.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job

import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

) : ViewModel() {
    private var viewModelJob = Job()

    init {
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}