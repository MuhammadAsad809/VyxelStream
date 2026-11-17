package com.primevision.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.primevision.app.data.plugin.Cs3PluginLoader
import com.primevision.app.ui.components.GlassNavigationBar
import com.primevision.app.ui.components.NavItem
import com.primevision.app.ui.theme.PrimeGlassBorder
import com.primevision.app.ui.theme.PrimeGlassPill
import com.primevision.app.ui.theme.PrimeObsidianGradient
import com.primevision.app.ui.theme.PrimePrimary
import com.primevision.app.ui.theme.PrimeTextMuted
import com.primevision.app.ui.theme.PrimeTextPrimary
import com.primevision.app.ui.theme.PrimeTextSecondary
import kotlinx.coroutines.launch

@Composable
fun ExploreScreen(
    onNavigate: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val plugins by Cs3PluginLoader.plugins.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimeObsidianGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Extensions",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Cloudstream .cs3 Runtime Engine",
                        color = PrimeTextSecondary,
                        fontSize = 13.sp
                    )
                }

                // Rescan button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0x33253633))
                        .border(1.dp, Color(0x44FFFFFF), RoundedCornerShape(20.dp))
                        .clickable {
                            scope.launch {
                                Cs3PluginLoader.initialize(context)
                                Toast.makeText(context, "Rescanned .cs3 extensions", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Scan",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Rescan",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notice Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0x1A14201E))
                    .border(1.dp, Color(0x22FFFFFF), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = "Extensions placed in /sdcard/Download/*.cs3 or bundled in assets are automatically decrypted and loaded via Dalvik DexClassLoader.",
                    color = Color(0xFF8E9E9C),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            LazyColumn(
                contentPadding = PaddingValues(bottom = 120.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (plugins.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Scanning for .cs3 extensions in /sdcard/Download/ ...",
                                color = PrimeTextMuted,
                                fontSize = 13.sp
                            )
                        }
                    }
                } else {
                    items(plugins) { plugin ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .background(PrimeGlassPill)
                                .border(1.dp, PrimeGlassBorder, RoundedCornerShape(18.dp))
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(if (plugin.isLoaded) Color(0x2E13B8A6) else Color(0x2EE55B5B)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Extension,
                                        contentDescription = "Plugin",
                                        tint = if (plugin.isLoaded) PrimePrimary else Color(0xFFE55B5B),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Column(modifier = Modifier.padding(start = 14.dp)) {
                                    Text(
                                        text = plugin.name,
                                        color = PrimeTextPrimary,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "v${plugin.version} • ${plugin.statusMessage}",
                                        color = if (plugin.isLoaded) Color(0xFF90A3A0) else Color(0xFFE55B5B),
                                        fontSize = 11.5.sp
                                    )
                                }
                            }

                            Icon(
                                imageVector = if (plugin.isLoaded) Icons.Default.CheckCircle else Icons.Default.Error,
                                contentDescription = if (plugin.isLoaded) "Loaded" else "Error",
                                tint = if (plugin.isLoaded) PrimePrimary else Color(0xFFE55B5B),
                                modifier = Modifier.size(22.dp)
                            )
                        }
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
