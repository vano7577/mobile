package ua.kpi.comsys.ip8404.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Голубов Iван\n" +
                "Група IП-84\n"+
                "ЗК IП-8404"
    }
    val text: LiveData<String> = _text
}