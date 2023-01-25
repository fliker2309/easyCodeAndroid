package com.example.emptyproject

class ViewModel(private val textObservable: TextObservable) {
   init {
       Model.init(object : TextCallback {
           override fun updateText(string: String) {
               textObservable.postValue(string)
           }
       })
   }

    fun init(){
        Model.start()
    }
}

class TextObservable {
    private lateinit var callback: TextCallback

    fun observe(callback: TextCallback) {
        this.callback
    }

    fun postValue(text: String) {
        callback.updateText(text)
    }
}

interface TextCallback {
    fun updateText(string: String)
}
