package kz.littlebrains.main.main_screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kz.littlebrains.core.CasesListModel
import kz.littlebrains.core.PiRepository
import kz.littlebrains.main.Log

interface IMainViewModel {
    val state: StateFlow<MainState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: MainEvent)
}

sealed class MainEvent{
    object Back: MainEvent()
    object NextScreen: MainEvent()
    object Load: MainEvent()
    class DetailScreen(val text: String): MainEvent()
}

sealed class NavigationEvent{
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }
    class Default: NavigationEvent()
    class Back: NavigationEvent()
    class NextScreen: NavigationEvent()
    class DetailScreen(val text: String): NavigationEvent()
}

sealed class MainState{
    class Default(): MainState()
    class Result(val text: CasesListModel?): MainState()
    class Error(val text: String): MainState()
    object Loading: MainState()
}

class MainViewModelPreview : IMainViewModel {
    override val state: StateFlow<MainState> = MutableStateFlow(MainState.Default()).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: MainEvent) {}
}

class MainViewModel(
    private val piRepository: PiRepository
): ScreenModel, IMainViewModel {

    private var _state = MutableStateFlow<MainState>(MainState.Default())
    override val state: StateFlow<MainState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    override fun sendEvent(event: MainEvent) {
        when(event){
            MainEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            MainEvent.NextScreen -> {
                _navigationEvent.value = NavigationEvent.NextScreen()
            }

            MainEvent.Load -> {
                _state.value = MainState.Loading
                screenModelScope.launch {
                    piRepository.fetchShows().apply {
                        if(isSuccessful){
                            _state.value = MainState.Result(body)
                        }
                        if(failed){
                            _state.value = MainState.Error(error.toString())
                        }
                    }
                }
            }

            is MainEvent.DetailScreen -> _navigationEvent.value = NavigationEvent.DetailScreen(event.text)
        }
    }
}