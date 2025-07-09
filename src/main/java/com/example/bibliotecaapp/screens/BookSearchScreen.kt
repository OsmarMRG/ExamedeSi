package com.example.bibliotecaapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Book(
    val title: String,
    val author: String,
    val isbn: String,
    val available: Boolean,
    val library: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    
    // Dados de exemplo
    val sampleBooks = listOf(
        Book("O Nome do Vento", "Patrick Rothfuss", "978-8580570", true, "Biblioteca Central"),
        Book("1984", "George Orwell", "978-0451524", false, "Biblioteca Norte"),
        Book("Dom Casmurro", "Machado de Assis", "978-8525406", true, "Biblioteca Sul"),
        Book("Harry Potter", "J.K. Rowling", "978-0439708", true, "Biblioteca Central"),
        Book("O Cortiço", "Aluísio Azevedo", "978-8508133", false, "Biblioteca Leste")
    )
    
    val filteredBooks = if (searchQuery.isEmpty()) {
        sampleBooks
    } else {
        sampleBooks.filter { 
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.author.contains(searchQuery, ignoreCase = true)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pesquisar Livros") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(Icons.Default.History, contentDescription = "Histórico")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
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
            // Campo de pesquisa
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisar por título ou autor") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp)
            )
            
            // Lista de livros
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredBooks) { book ->
                    BookCard(
                        book = book,
                        onReserve = { /* Implementar reserva */ }
                    )
                }
            }
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    onReserve: () -> Unit
) {
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
                        text = book.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "por ${book.author}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "ISBN: ${book.isbn}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = book.library,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (book.available) "Disponível" else "Indisponível",
                    fontSize = 14.sp,
                    color = if (book.available) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Medium
                )
                
                Button(
                    onClick = onReserve,
                    enabled = book.available,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Reservar")
                }
            }
        }
    }
}