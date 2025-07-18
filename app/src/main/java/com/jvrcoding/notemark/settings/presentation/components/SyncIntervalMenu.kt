package com.jvrcoding.notemark.settings.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.jvrcoding.notemark.core.presentation.designsystem.theme.CheckIcon

@Composable
fun SyncIntervalMenu(
    menuItems: List<DropDownItem> = emptyList(),
    onMenuItemClick: (Int) -> Unit = {},
    isExpanded: Boolean,
    onDismiss: () -> Unit
) {

    DropdownMenu(
        modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        properties = PopupProperties(clippingEnabled = true),
        shape = MenuDefaults.shape,
        expanded = isExpanded,
        containerColor = Color.White,
        onDismissRequest = onDismiss,
    ) {
        menuItems.forEachIndexed { index, dropDownItem ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = dropDownItem.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                trailingIcon = {
                    if(dropDownItem.isSelected) {
                        Icon(
                            imageVector = CheckIcon,
                            contentDescription = dropDownItem.title,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                onClick = { }
            )
        }
    }

}