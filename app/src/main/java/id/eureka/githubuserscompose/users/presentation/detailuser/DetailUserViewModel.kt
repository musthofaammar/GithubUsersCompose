package id.eureka.githubuserscompose.users.presentation.detailuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.eureka.githubuserscompose.core.model.Result
import id.eureka.githubuserscompose.users.domain.usecase.GetRepositoriesUseCase
import id.eureka.githubuserscompose.users.domain.usecase.GetUserByUserIdOrUserNameUseCase
import id.eureka.githubuserscompose.users.presentation.model.DetailUserUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val getUserByUserIdOrUserNameUseCase: GetUserByUserIdOrUserNameUseCase,
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
) : ViewModel() {

    private val _detailUserState: MutableStateFlow<DetailUserUIState> =
        MutableStateFlow(DetailUserUIState.Empty)
    val detailUserState = _detailUserState.asStateFlow()

    fun getUserDetail(userName: String, userId: Int) {
        viewModelScope.launch {
            getUserByUserIdOrUserNameUseCase.getUserByUserIdOrUserName(userName, userId)
                .collectLatest { result ->
                    when (result) {
                        is Result.Error -> _detailUserState.value =
                            DetailUserUIState.Error(result.message)
                        is Result.Success -> _detailUserState.value =
                            DetailUserUIState.GetUserDetailSuccess(result.data)
                    }
                }
        }
    }

    fun getUserRepositories(userName: String, userId: Int) {
        viewModelScope.launch {

            getRepositoriesUseCase.getRepositories(userName, userId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collectLatest { result ->
                    _detailUserState.value = DetailUserUIState.GetUserRepositoriesSuccess(result)
                }
        }
    }
}