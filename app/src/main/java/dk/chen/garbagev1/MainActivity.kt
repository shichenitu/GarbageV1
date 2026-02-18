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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dk.chen.garbagev1.ui.theme.GarbageSortingScreen
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme

class MainActivity : ComponentActivity() {
    val myFileContent = """
        newspaper, paper
        magazine, paper
        milk carton, plastic
        shoe box, cardboard
        can, metal
        book, paper
        aluminium foil (clean), metal
        teddy bears, daily waste
        flower pot (plastic), daily waste
        cables, metal
        envelopes, paper
        detergents, plastic
        musical instrument, wood
        cookware, metal
        hammer, metal
        curtain clips, metal
        jars, glass
        carpets, bulky waste
        postcards, cardboard
        chips bag, other
        tooth brush, plastic
        shampoo bottle, plastic
        capsule, metal
        needle, metal
        letter, paper
        plastic bottle, plastic
        meat, food waste
        clothes, other
        cutlery, metal
        paint, chemical
        chlorine, chemical
        computer, electronics
        battery, batteries
        printer, electronics
        potato, food
        cabbage, food
        kale, food
        cauliflower, food
        onion, food
        beetroot, food
        celeriac, food
        cellery, food
        flour, food
        sugar, food
        rice, food
    """.trimIndent()

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
        ItemsDB.populateItems(myFileContent)
        showToast("LIFECYCLE: onCreate()")
        Log.d("LIFECYCLE", "onCreate()")
        enableEdgeToEdge()
        setContent {
            GarbageV1Theme() {
                val snackbarHostState = remember { SnackbarHostState() }
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
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }) { innerPadding ->
                    GarbageSortingScreen(
                        modifier = Modifier.padding(paddingValues = innerPadding) ,
                        snackbarHostState = snackbarHostState
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