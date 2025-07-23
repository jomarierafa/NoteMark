package com.jvrcoding.notemark.settings.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.presentation.designsystem.components.NMToolbar
import com.jvrcoding.notemark.core.presentation.designsystem.theme.ChevronRight
import com.jvrcoding.notemark.core.presentation.designsystem.theme.ClockIcon
import com.jvrcoding.notemark.core.presentation.designsystem.theme.ExtraSmallTitle
import com.jvrcoding.notemark.core.presentation.designsystem.theme.LogoutIcon
import com.jvrcoding.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.jvrcoding.notemark.core.presentation.designsystem.theme.ReloadIcon
import com.jvrcoding.notemark.core.presentation.util.ObserveAsEvents
import com.jvrcoding.notemark.settings.presentation.components.DropDownItem
import com.jvrcoding.notemark.settings.presentation.components.SyncIntervalMenu
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenRoot(
    onBackClick: () -> Unit,
    onSuccessfulLogout: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            is SettingsEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            is SettingsEvent.OnSuccessfulLogout -> onSuccessfulLogout()
        }
    }
    SettingsScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                SettingsAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    var showDropDown by remember {
        mutableStateOf(false)
    }
    Scaffold(
        containerColor =  MaterialTheme.colorScheme.surfaceContainerLowest,
        topBar = {
            NMToolbar(
                showNavigationIcon = true,
                showNoInternetIcon = false,
                titleStyle = ExtraSmallTitle,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .padding(),
                title = stringResource(R.string.settings),
                onNavIconClick = { onAction(SettingsAction.OnBackClick) },
            )
        },
        modifier = Modifier.fillMaxSize()

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable { showDropDown = true }
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ClockIcon,
                    contentDescription = stringResource(R.string.clock_icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.sync_interval),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.syncInterval.label,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    SyncIntervalMenu(
                        isExpanded = showDropDown,
                        onDismiss = { showDropDown = false },
                        menuItems = listOf(
                            DropDownItem(
                                title = stringResource(R.string.manual_only),
                                isSelected = state.syncInterval == SyncInterval.Manual
                            ),
                            DropDownItem(
                                title = stringResource(R.string._15_minutes),
                                isSelected = state.syncInterval == SyncInterval.FifteenMinutes
                            ),
                            DropDownItem(
                                title = stringResource(R.string._30_minutes),
                                isSelected = state.syncInterval == SyncInterval.ThirtyMinutes
                            ),
                            DropDownItem(
                                title = stringResource(R.string._1_hour),
                                isSelected = state.syncInterval == SyncInterval.OneHour
                            )
                        ),
                        onMenuItemClick = { index ->
                            when(index) {
                                0 -> onAction(SettingsAction.OnSyncIntervalChange(SyncInterval.Manual))
                                1 -> onAction(SettingsAction.OnSyncIntervalChange(SyncInterval.FifteenMinutes))
                                2 -> onAction(SettingsAction.OnSyncIntervalChange(SyncInterval.ThirtyMinutes))
                                3 -> onAction(SettingsAction.OnSyncIntervalChange(SyncInterval.OneHour))
                            }
                        }
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.surface,
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .clickable { onAction(SettingsAction.OnSyncDataClick) }
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp),
            ) {
                Icon(
                    imageVector = ReloadIcon,
                    contentDescription = stringResource(R.string.reload_icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = stringResource(R.string.sync_data),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = state.lastSync,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.surface,
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .clickable { onAction(SettingsAction.OnLogoutClick) }
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = LogoutIcon,
                    contentDescription = stringResource(R.string.logout_icon),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.log_out),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    NoteMarkTheme {
        SettingsScreen(
            state = SettingsState(),
            onAction = {}
        )
    }
}