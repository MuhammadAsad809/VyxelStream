package com.primevision.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.primevision.app.data.plugin.ExtensionBridge
import com.primevision.app.ui.components.GlassNavigationBar
import com.primevision.app.ui.components.NavItem
import com.primevision.app.ui.theme.PrimeGlassBorder
import com.primevision.app.ui.theme.PrimeGlassPill
import com.primevision.app.ui.theme.PrimeObsidianGradient
import com.primevision.app.ui.theme.PrimePrimary
import com.primevision.app.ui.theme.PrimeTextMuted
import com.primevision.app.ui.theme.PrimeTextPrimary
import com.primevision.app.ui.theme.PrimeTextSecondary

@Composable
fun ExploreScreen(
    onNavigate: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val extensions = ExtensionBridge.getInstalledExtensions()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimeObsidianGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Extensions & Providers",
                color = PrimeTextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Dynamic Cloudstream .cs3 plugin engine connected",
                color = PrimeTextSecondary,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                contentPadding = PaddingValues(bottom = 120.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(extensions) { ext ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(PrimeGlassPill)
                            .border(1.dp, PrimeGlassBorder, RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x3313B8A6)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Extension,
                                    contentDescription = "Extension",
                                    tint = PrimePrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(
                                    text = ext.name,
                                    color = PrimeTextPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "v${ext.version} • ${ext.pluginClassName.substringBeforeLast(".")}",
                                    color = PrimeTextMuted,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Active",
                            tint = PrimePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        GlassNavigationBar(
            selectedItem = NavItem.EXPLORE,
            onItemSelected = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
