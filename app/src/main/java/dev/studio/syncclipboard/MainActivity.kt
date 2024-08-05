package dev.studio.syncclipboard

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.studio.syncclipboard.ui.theme.SyncClipboardTheme

class MainActivity : ComponentActivity() {
    private lateinit var clipboardManager: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SyncClipboardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
        val serviceIntent = Intent(this, ClipboardService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        val server = Server()
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.addPrimaryClipChangedListener {
            val clipData = clipboardManager.primaryClip
            val item = clipData?.getItemAt(0)
            val clipboardText = item?.text.toString()
            server.clientSever(clipboardText)
            Log.i("4564564664564",clipboardText)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val value = remember {
        mutableStateOf("sadasdsadas")
    }
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        TextField(value =value.value , onValueChange ={value.value = it} )

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SyncClipboardTheme {
        Greeting("Android")
    }
}