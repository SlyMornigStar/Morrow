package app.morrow.messenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF18251F)
private val Moss = Color(0xFF3F6B55)
private val Canvas = Color(0xFFF6F5EF)
private val Quiet = Color(0xFF78827A)
private val Line = Color(0xFFE7E8DF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MorrowApp() }
    }
}

private enum class Destination(val label: String, val icon: ImageVector) {
    Chats("Chats", Icons.Filled.ChatBubbleOutline),
    Contacts("Contacts", Icons.Filled.Contacts),
    Calls("Calls", Icons.Filled.Call),
    Settings("Settings", Icons.Filled.Settings),
}

private data class SampleChat(
    val name: String,
    val preview: String,
    val time: String,
    val initials: String,
    val tint: Color,
    val unread: Int = 0,
)

private val sampleChats = listOf(
    SampleChat("Maya Chen", "That little bookshop was perfect 🌿", "10:42", "MC", Color(0xFFDCE9D9), 2),
    SampleChat("Jonah Rivera", "Voice message · 0:18", "09:16", "JR", Color(0xFFF0E2CD)),
    SampleChat("Amara Okafor", "Sent you a photo", "Yesterday", "AO", Color(0xFFE9DDE9), 1),
    SampleChat("Theo Martin", "See you after work", "Yesterday", "TM", Color(0xFFDDE8EC)),
    SampleChat("Priya Nair", "Thank you!", "Mon", "PN", Color(0xFFF0E3D7)),
)

@Composable
private fun MorrowApp() {
    var selected by remember { mutableStateOf(Destination.Chats) }
    var query by remember { mutableStateOf("") }

    MaterialTheme {
        Scaffold(
            containerColor = Canvas,
            bottomBar = {
                NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
                    Destination.entries.forEach { destination ->
                        NavigationBarItem(
                            selected = selected == destination,
                            onClick = { selected = destination },
                            icon = { Icon(destination.icon, contentDescription = destination.label) },
                            label = { Text(destination.label, fontSize = 11.sp) },
                            alwaysShowLabel = true,
                        )
                    }
                }
            },
        ) { padding ->
            Column(Modifier.fillMaxSize().padding(padding)) {
                if (selected == Destination.Chats) {
                    ChatsScreen(query = query, onQueryChange = { query = it })
                } else {
                    DestinationPlaceholder(selected)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatsScreen(query: String, onQueryChange: (String) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text("morrow", color = Moss, fontSize = 15.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
                Text("Your people, in one place.", color = Quiet, fontSize = 13.sp)
            }
            Box(
                Modifier.size(42.dp).clip(CircleShape).background(Moss),
                contentAlignment = Alignment.Center,
            ) { Text("+", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Light) }
        }

        Text("Chats", Modifier.padding(start = 24.dp, top = 18.dp, bottom = 14.dp), color = Ink, fontSize = 30.sp, fontWeight = FontWeight.SemiBold)

        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(52.dp),
            placeholder = { Text("Find a conversation", color = Quiet, fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Quiet) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
        Spacer(Modifier.height(16.dp))

        val filtered = sampleChats.filter { it.name.contains(query, ignoreCase = true) || it.preview.contains(query, ignoreCase = true) }
        LazyColumn(Modifier.fillMaxSize()) {
            items(filtered) { chat -> ChatRow(chat) }
            item {
                Text(
                    "Sample conversations · TDLib not connected",
                    Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 18.dp),
                    color = Quiet,
                    fontSize = 11.sp,
                )
            }
        }
    }
}

@Composable
private fun ChatRow(chat: SampleChat) {
    Row(
        Modifier.fillMaxWidth().clickable { }.padding(horizontal = 22.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(54.dp).clip(CircleShape).background(chat.tint), contentAlignment = Alignment.Center) {
            Text(chat.initials, color = Ink, fontWeight = FontWeight.Medium, fontSize = 15.sp)
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(chat.name, color = Ink, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(Modifier.height(4.dp))
            Text(chat.preview, color = Quiet, fontSize = 13.sp, maxLines = 1)
        }
        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
            Text(chat.time, color = Quiet, fontSize = 11.sp)
            if (chat.unread > 0) {
                Spacer(Modifier.height(6.dp))
                Box(Modifier.size(20.dp).clip(CircleShape).background(Moss), contentAlignment = Alignment.Center) {
                    Text(chat.unread.toString(), color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
    Box(Modifier.fillMaxWidth().padding(start = 90.dp, end = 22.dp).height(1.dp).background(Line))
}

@Composable
private fun DestinationPlaceholder(destination: Destination) {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(destination.icon, contentDescription = null, tint = Moss, modifier = Modifier.size(34.dp))
        Spacer(Modifier.height(12.dp))
        Text(destination.label, color = Ink, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("This area is planned for a later stage.", color = Quiet, fontSize = 13.sp, modifier = Modifier.padding(top = 6.dp))
    }
}
