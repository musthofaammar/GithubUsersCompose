package id.eureka.githubuserscompose.users.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.eureka.githubusers.users.domain.usecase.SearchUserUseCase
import id.eureka.githubuserscompose.R
import id.eureka.githubuserscompose.core.provider.DispatcherProvider
import id.eureka.githubuserscompose.core.provider.ResourceProvider
import id.eureka.githubuserscompose.users.presentation.model.UserUIState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private var job : Job? = null

    private val _userUiState: MutableStateFlow<UserUIState> = MutableStateFlow(UserUIState.Empty)
    val userUiState = _userUiState.asStateFlow()

    fun searchUser(userName: String) {
        job?.cancel()

        job = viewModelScope.launch {
            flowOf(userName)
                .debounce(1000)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    _userUiState.value = UserUIState.Loading
                    searchUserUseCase.searchUser(query)
                }
                .flowOn(dispatcherProvider.getDefault())
                .catch { exception ->
                    _userUiState.value = UserUIState.Error(
                        exception.localizedMessage ?: resourceProvider.getString(
                            R.string.something_wrong
                        )
                    )
                }
                .cachedIn(viewModelScope)
                .collectLatest { result ->
                    _userUiState.value =
                        UserUIState.SearchUserSuccess(result)
                }
        }
    }
}