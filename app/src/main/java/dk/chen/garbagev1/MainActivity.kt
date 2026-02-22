package dk.chen.garbagev1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import dk.chen.garbagev1.ui.components.SnackBarHandler
import dk.chen.garbagev1.ui.features.GarbageSortingScreen
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var snackBarHandler: SnackBarHandler

    // Helper function to easily show a Toast
    private fun showToast(message: String) {
        Toast.makeText(
            /* context = */ this,
            /* text = */ message,
            /* duration = */ Toast.LENGTH_SHORT
        ).show()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showToast("LIFECYCLE: onCreate()")
        Log.d("LIFECYCLE", "onCreate()")
        enableEdgeToEdge()

        setContent {
            GarbageV1Theme() {
                val hostState = snackBarHandler.snackBarHostState

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorScheme.primaryContainer
                            )
                        )
                    },
                    snackbarHost = { SnackbarHost(hostState = hostState) }
                ) { innerPadding ->
                    GarbageSortingScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        showToast("LIFECYCLE: onStart()")
        Log.d("LIFECYCLE", "onStart() called")
    }


    override fun onResume() {
        super.onResume()
        showToast("LIFECYCLE: onResume()")
        Log.d("LIFECYCLE", "onResume() called")
    }


    override fun onPause() {
        super.onPause()
        showToast("LIFECYCLE: onPause()")
        Log.d("LIFECYCLE", "onPause() called")
    }


    override fun onStop() {
        super.onStop()
        showToast("LIFECYCLE: onStop()")
        Log.d("LIFECYCLE", "onStop() called")
    }


    override fun onDestroy() {
        super.onDestroy()
        showToast("LIFECYCLE: onDestroy()")
        Log.d("LIFECYCLE", "onDestroy() called")
    }
}