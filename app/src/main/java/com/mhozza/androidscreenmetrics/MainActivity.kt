package com.mhozza.androidscreenmetrics

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display.DEFAULT_DISPLAY
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.mhozza.androidscreenmetrics.ui.theme.AndroidScreenMetricsTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    setContent {
      AndroidScreenMetricsTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing), color = MaterialTheme.colorScheme.background) {
          Content()
        }
      }
    }
  }
}

@Composable
fun Content() {
  Column(
    Modifier
      .fillMaxSize()
      .padding(8.dp)) {
    val displayManager = LocalContext.current.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    val display = displayManager.getDisplay(DEFAULT_DISPLAY)

    with(DisplayMetrics()) {
      // We cannot use activity based information here because this class is injected in a
      // singleton component.
      @Suppress("DEPRECATION") display.getRealMetrics(this)

      Text(text = "Screen width px: $widthPixels")
      Text(text = "Screen height px: $heightPixels")

      Text(text = "xDPI: $xdpi")
      Text(text = "yDPI: $ydpi")
      Text(text = "density: $density")
      Text(text = "densityDPI: $densityDpi")

      with(LocalDensity.current) {
        Text(text = "Screen width dp: ${widthPixels.toDp()}")
        Text(text = "Screen height dp: ${heightPixels.toDp()}")
      }
    }
    Text(text = "Status bar height: ${WindowInsets.statusBars.asPaddingValues().calculateTopPadding()}")
    Text(text = "Navigation bar height: ${WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()}")

    Box(modifier = Modifier
      .background(Color.Red)
      .width(160.dp)
      .height(160.dp)) {
      Text(text = "160dp x 160dp\n1\" x 1\"", Modifier.align(Center), textAlign = TextAlign.Center)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
  AndroidScreenMetricsTheme {
    Content()
  }
}