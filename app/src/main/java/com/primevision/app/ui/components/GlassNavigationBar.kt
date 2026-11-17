package com.primevision.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.primevision.app.ui.theme.PrimeGlassBorder
import com.primevision.app.ui.theme.PrimeTextMuted
import com.primevision.app.ui.theme.PrimeTextPrimary

enum class NavItem(val icon: ImageVector, val label: String) {
    HOME(Icons.Default.Home, "Home"),
    EXPLORE(Icons.Default.Explore, "Explore"),
    FAVORITES(Icons.Default.Favorite, "Favorites"),
    PROFILE(Icons.Default.Person, "Profile")
}

@Composable
fun GlassNavigationBar(
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val pillShape = RoundedCornerShape(32.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(58.dp)
                .clip(pillShape)
                .background(Color(0xDD0A1716))
                .border(width = 1.dp, color = PrimeGlassBorder, shape = pillShape)
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem.entries.forEach { item ->
                val isSelected = item == selectedItem
                val iconColor by animateColorAsState(
                    targetValue = if (isSelected) PrimeTextPrimary else PrimeTextMuted,
                    label = "nav_icon_color"
                )
                val circleBackground by animateColorAsState(
                    targetValue = if (isSelected) Color(0x38FFFFFF) else Color.Transparent,
                    label = "nav_circle_bg"
                )

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(circleBackground)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onItemSelected(item) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = iconColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}
