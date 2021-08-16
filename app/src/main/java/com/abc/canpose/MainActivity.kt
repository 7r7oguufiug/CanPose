package com.abc.canpose

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abc.canpose.ui.theme.CanPoseTheme

class MainActivity : ComponentActivity() {

    private val statusViewModule: StatusViewModule by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(ConstraintLayout(this))
        setContent {
            CanPoseTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android", statusViewModule.clicked)
                }
            }
        }
    }
}

class StatusViewModule : ViewModel() {
    val clicked = mutableStateOf(false)
}

@Composable
fun Greeting(name: String, clicked: MutableState<Boolean>) {
    Row {
        val text = if (clicked.value) {
            "Hello ${name}!"
        } else {
            "Bye ${name}!"
        }
        Text(text = text)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val text = if (clicked.value) {
            "点击了"
        } else {
            "点击"
        }
        Button(
            onClick = { clicked.value = !clicked.value },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text(text = text, fontSize = 13.sp)
        }
    }
}

fun Greeting(context: Context, name: String, clicked: MutableLiveData<Boolean>) {
    val textView: TextView = TextView(context)
    val button: Button = Button(context)

    //控件添加逻辑
    //...

    val updateTextView = {
        val text = if (clicked.value == true) {
            "Hello ${name}!"
        } else {
            "Bye ${name}!"
        }
        textView.text = text
    }
    val updateButton = {
        val text = if (clicked.value == true) {
            "点击了"
        } else {
            "点击"
        }
        button.text = text
    }

    button.setOnClickListener {
        clicked.value?.let { status ->
            clicked.value = !status
        }
        updateTextView()
        updateButton()
    }
    updateTextView()
    updateButton()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CanPoseTheme {
        Greeting("Android", remember {
            mutableStateOf(false)
        })
    }
}