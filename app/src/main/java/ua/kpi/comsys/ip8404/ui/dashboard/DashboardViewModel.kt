package ua.kpi.comsys.ip8404.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    var prev: DashboardStage? = null
    val stage = MutableLiveData<DashboardStage>(DashboardStage.Plot)

    fun changeStage(newStage: DashboardStage) {
        val current = stage.value
        if (newStage != current) {
            prev = current
            stage.value = newStage
        }
    }
}