package net.starry.cleanarch.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import net.starry.cleanarch.domain.model.GitRepo
import net.starry.cleanarch.domain.model.GitUser
import net.starry.cleanarch.domain.usecase.GetUserReposUseCase
import net.starry.cleanarch.presentation.base.BaseViewModel
import net.starry.cleanarch.presentation.model.MainListItem
import net.starry.cleanarch.presentation.model.PopupMessage
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserRepos: GetUserReposUseCase
) : BaseViewModel() {

    private val _refreshListData = MutableLiveData<List<MainListItem>>()
    val refreshListData: LiveData<List<MainListItem>> = _refreshListData

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    init {

    }

    fun getGitUserRepo(userName: String) {
        viewModelScope.launch {
            getUserRepos(GetUserReposUseCase.Params(userName))
                .onSuccess {
                    val list = mutableListOf<MainListItem>()
                    list.add(MainListItem.Header(it.first))
                    list.addAll(
                        it.second.map { repo ->
                            MainListItem.RepoItem(repo)
                        }
                    )
                    _refreshListData.value = list
                }
                .onFailure {
                    showAlertDialog(PopupMessage("오류", it.message))
                }
                .commonErrorHandler {
                    // TODO 재시도 필요한 경우 로직.
                }
                .load { isLoading ->
                    _showProgress.value = isLoading
                }
        }
    }

    fun onClickUser(user: GitUser) {

    }

    fun onClickRepo(user: GitUser, repo: GitRepo) {

    }

    override fun onCleared() {
        super.onCleared()
    }

}