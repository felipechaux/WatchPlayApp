package com.example.watchplayapp.ui.component

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestLocationPermission(
    executeEvent: () -> Unit,
) {
    val context = LocalContext.current

    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.all { it }
        if (areGranted) {
            Log.e("", "location is granted")
            executeEvent.invoke()
        } else {
            Log.e("", "location is not granted")
        }
    }

    LaunchedEffect(Unit) {
        checkAndRequestLocationPermission(
            context,
            permissions,
            launcherMultiplePermissions,
            executeEvent = {
                executeEvent.invoke()
            },
        )
    }
}

fun checkAndRequestLocationPermission(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    executeEvent: () -> Unit,
) {
    if (permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        executeEvent.invoke()
    } else {
        // Request permissions
        launcher.launch(permissions)
    }
}
