
const API_URL = 'http://localhost:8080/api';

// Elementos del DOM
const resultadosContainer = document.getElementById('resultadosContainer');
const searchInput = document.getElementById('searchInput');
const searchButton = document.getElementById('searchButton');

// Cargar resultados de búsqueda por parámetro en la URL
document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const searchTerm = urlParams.get('q');
    
    if (searchTerm) {
        searchInput.value = searchTerm;
        buscarLibros(searchTerm);
    } else {
        resultadosContainer.innerHTML = '<div class="col-12 text-center">Ingresa un término de búsqueda para encontrar libros</div>';
    }
    
    // Configurar eventos
    searchButton.addEventListener('click', () => {
        const searchTerm = searchInput.value.trim();
        if (searchTerm) {
            buscarLibros(searchTerm);
            // Actualizar la URL con el término de búsqueda
            const url = new URL(window.location);
            url.searchParams.set('q', searchTerm);
            window.history.pushState({}, '', url);
        }
    });
    
    // Manejar la búsqueda con tecla Enter
    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            searchButton.click();
        }
    });
});

// Función para buscar libros
function buscarLibros(termino) {
    mostrarCargando();
    
    fetch(`${API_URL}/buscar?titulo=${encodeURIComponent(termino)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la búsqueda');
            }
            return response.json();
        })
        .then(resultados => {
            mostrarResultados(resultados);
        })
        .catch(error => {
            console.error('Error:', error);
            mostrarError(`Error en la búsqueda: ${error.message}`);
        });
}

// Función para mostrar resultados
function mostrarResultados(resultados) {
    if (!resultados || resultados.length === 0) {
        resultadosContainer.innerHTML = '<div class="col-12 text-center">No se encontraron resultados</div>';
        return;
    }
    
    let html = '';
    resultados.forEach(libro => {
        const portadaUrl = libro.formats && libro.formats['image/jpeg'] 
            ? libro.formats['image/jpeg'] 
            : 'https://via.placeholder.com/150x220?text=Sin+Portada';
        
        const autores = libro.autor && libro.autor.length > 0 
            ? libro.autor.map(a => a.nombre).join(', ')
            : 'Autor desconocido';
        
        html += `
        <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
            <div class="card libro-card">
                <div class="card-img-container">
                    <img src="${portadaUrl}" class="card-img-top" alt="${libro.titulo}">
                </div>
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title book-title">${libro.titulo}</h5>
                    <p class="card-text book-author">${autores}</p>
                    <button class="btn btn-success mt-auto guardar-btn" data-libro='${JSON.stringify(libro)}'>
                        Guardar en mi biblioteca
                    </button>
                </div>
            </div>
        </div>
        `;
    });
    
    resultadosContainer.innerHTML = html;
    
    // Configurar eventos para botones de guardar
    document.querySelectorAll('.guardar-btn').forEach(button => {
        button.addEventListener('click', (e) => {
            const libroData = JSON.parse(e.target.dataset.libro);
            guardarLibro(libroData);
        });
    });
}

// Función para guardar un libro en la base de datos
function guardarLibro(libro) {
    fetch(`${API_URL}/libros`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(libro)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al guardar el libro');
        }
        return response.json();
    })
    .then(data => {
        alert('Libro guardado con éxito');
        window.location.href = `detalle.html?id=${data.id}`;
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al guardar el libro: ' + error.message);
    });
}

// Funciones auxiliares
function mostrarCargando() {
    resultadosContainer.innerHTML = '<div class="col-12 text-center"><div class="spinner-border text-primary" role="status"><span class="visually-hidden">Buscando...</span></div></div>';
}

function mostrarError(mensaje) {
    resultadosContainer.innerHTML = `<div class="col-12 text-center text-danger">${mensaje}</div>`;
}