package ru.verb.aston_intensive_hometask_2_2_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import ru.verb.aston_intensive_hometask_2_2_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var state: State

    companion object {
        const val STATE_KEY = "STATE_KEY"
    }

    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        state = savedInstanceState?.getParcelable(STATE_KEY, State::class.java) ?: State(
            count = 0,
            message = "Hello"
        )
        with(binding) {
            showCount.text = state.count.toString()
            textEdit.setText(state.message)
            buttonCount.setOnClickListener {
                incrementCount()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        state = state.copy(message = binding.textEdit.text.toString())
        outState.putParcelable(STATE_KEY, state)
    }

    private fun incrementCount() {
        state = state.copy(count = state.count + 1)
        renderState()
    }

    private fun renderState() {
        binding.showCount.text = state.count.toString()
    }
}

data class State(
    val count: Int,
    val message: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<State> {
        override fun createFromParcel(parcel: Parcel): State {
            return State(parcel)
        }

        override fun newArray(size: Int): Array<State?> {
            return arrayOfNulls(size)
        }
    }
}