package kz.littlebrains.main.Scanner_screen

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface IScannerViewModel {
    val state: StateFlow<ScannerState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: ScannerEvent)
}

sealed class ScannerEvent{
    object Back: ScannerEvent()
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

sealed class ScannerState{
    class Default(val text: String): ScannerState()
}

class ScannerViewModelPreview : IScannerViewModel {
    override val state: StateFlow<ScannerState> = MutableStateFlow(ScannerState.Default("Scanner")).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: ScannerEvent) {}
}

class ScannerViewModel: ScreenModel, IScannerViewModel {

    private var _state = MutableStateFlow<ScannerState>(ScannerState.Default("Scanner"))
    override val state: StateFlow<ScannerState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    override fun sendEvent(event: ScannerEvent) {
        when(event){
            ScannerEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }


}