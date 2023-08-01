package com.ics342.labs

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ics342.labs.ui.theme.LabsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var hasPermission by remember { mutableStateOf(false) }
            var showPermissionRationale by remember { mutableStateOf(false) }
            val context = LocalContext.current

            val launcher = rememberLauncherForActivityResult(
                RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    hasPermission = true
                } else {
                    showPermissionRationale = shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                }
            }

            LabsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        if (hasPermission) {
                            LocationView()
                        } else {
                            Button(onClick = {
                                checkOrRequestPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS,
                                    launcher
                                ) { hasPermission = true }
                            }) {
                                Text("Request Notification Permission")
                            }
                        }

                        if (showPermissionRationale) {
                            PermissionRationaleDialog(
                                onConfirm = { launcher.launch(Manifest.permission.POST_NOTIFICATIONS) },
                                onCancel = { showPermissionRationale = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LocationView() {
    Text("Has Notification Permission")
}

@Composable
fun PermissionRationaleDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = { onCancel() }) {
                Text("No")
            }
        },
        title = {
            Text("Permissions Required")
        },
        text = {
            Text("This app requires notification permission to show notifications.")
        }
    )
}

private fun checkOrRequestPermission(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    permissionGranted: () -> Unit
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, permission) -> {
            permissionGranted()
        }
        else -> {
            launcher.launch(permission)
        }
    }
}