package com.example.bibliotecaapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Reservation(
    val bookTitle: String,
    val author: String,
    val reservationDate: String,
    val status: ReservationStatus,
    val library: String,
    val dueDate: String? = null
)

enum class ReservationStatus {
    ACTIVE, COMPLETED, EXPIRED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationHistoryScreen(
    onNavigateBack: () -> Unit
) {
    // Dados de exemplo
    val sampleReservations = listOf(
        Reservation(
            "O Nome do Vento",
            "Patrick Rothfuss",
            "2 de agosto 2023",
            ReservationStatus.ACTIVE,
            "Biblioteca Central",
            "15 de agosto 2023"
        ),
        Reservation(
            "1984",
            "George Orwell",
            "3 de maio 2023",
            ReservationStatus.COMPLETED,
            "Biblioteca Norte"
        ),
        Reservation(
            "Dom Casmurro",
            "Machado de Assis",
            "10 de abril 2023",
            ReservationStatus.EXPIRED,
            "Biblioteca Sul"
        )
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Histórico de Reservas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (sampleReservations.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Nenhuma reserva encontrada",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(sampleReservations) { reservation ->
                        ReservationCard(reservation = reservation)
                    }
                }
            }
        }
    }
}

@Composable
fun ReservationCard(reservation: Reservation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = reservation.bookTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "por ${reservation.author}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = reservation.library,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                
                Icon(
                    imageVector = when (reservation.status) {
                        ReservationStatus.ACTIVE -> Icons.Default.Schedule
                        ReservationStatus.COMPLETED -> Icons.Default.CheckCircle
                        ReservationStatus.EXPIRED -> Icons.Default.Schedule
                    },
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = when (reservation.status) {
                        ReservationStatus.ACTIVE -> MaterialTheme.colorScheme.primary
                        ReservationStatus.COMPLETED -> MaterialTheme.colorScheme.primary
                        ReservationStatus.EXPIRED -> MaterialTheme.colorScheme.error
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Reservado em: ${reservation.reservationDate}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (reservation.dueDate != null) {
                        Text(
                            text = "Prazo: ${reservation.dueDate}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                
                Text(
                    text = when (reservation.status) {
                        ReservationStatus.ACTIVE -> "Ativa"
                        ReservationStatus.COMPLETED -> "Concluída"
                        ReservationStatus.EXPIRED -> "Expirada"
                    },
                    fontSize = 14.sp,
                    color = when (reservation.status) {
                        ReservationStatus.ACTIVE -> MaterialTheme.colorScheme.primary
                        ReservationStatus.COMPLETED -> MaterialTheme.colorScheme.primary
                        ReservationStatus.EXPIRED -> MaterialTheme.colorScheme.error
                    },
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}