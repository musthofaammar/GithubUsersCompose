package id.eureka.githubuserscompose.users.presentation.model

import androidx.paging.PagingData

sealed interface DetailUserUIState {
    object Loading : DetailUserUIState
    data class Error(val message: String) : DetailUserUIState
    data class GetUserDetailSuccess(val data: User) : DetailUserUIState
    data class GetUserRepositoriesSuccess(val data: PagingData<Repository>) : DetailUserUIState
    object Empty : DetailUserUIState
}