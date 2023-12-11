package kz.littlebrains.main.DetailScreen

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface IDetailViewModel {
    val state: StateFlow<DetailState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: DetailEvent)
}

sealed class DetailEvent{
    object Back: DetailEvent()
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
}

sealed class DetailState{
    class Default(val text: String): DetailState()
}

class DetailViewModelPreview : IDetailViewModel {
    override val state: StateFlow<DetailState> = MutableStateFlow(DetailState.Default("Detail")).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: DetailEvent) {}
}

class DetailViewModel: ScreenModel, IDetailViewModel {

    private var _state = MutableStateFlow<DetailState>(DetailState.Default("Detail"))
    override val state: StateFlow<DetailState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    override fun sendEvent(event: DetailEvent) {
        when(event){
            DetailEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }


}