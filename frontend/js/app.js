
const API_URL = 'http://localhost:8080/api';

// Elementos del DOM
const librosContainer = document.getElementById('librosContainer');
const searchInput = document.getElementById('searchInput');
const searchButton = document.getElementById('searchButton');
const filterButtons = document.querySelectorAll('.filter-btn');

// Cargar libros al iniciar la página
document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('[data-idioma="all"]').classList.add('active');
    
    cargarLibros();
    
    // Configurar evento de búsqueda
    if (searchButton) {
        searchButton.addEventListener('click', () => {
            const searchTerm = searchInput.value.trim();
            if (searchTerm) {
                window.location.href = `pages/busqueda.html?q=${encodeURIComponent(searchTerm)}`;
            }
        });
    }
    
    // Configurar eventos para filtros
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            
            filterButtons.forEach(btn => btn.classList.remove('active'));
            
            button.classList.add('active');
            
            const idioma = button.dataset.idioma;
            if (idioma === 'all') {
                cargarLibros();
            } else {
                cargarLibrosPorIdioma(idioma);
            }
        });
    });
    
    // Manejar la búsqueda con tecla Enter
    if (searchInput) {
        searchInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                searchButton.click();
            }
        });
    }
});

// Función para cargar todos los libros
function cargarLibros() {
    mostrarCargando();
    
    fetch(`${API_URL}/libros`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los libros');
            }
            return response.json();
        })
        .then(libros => {
            mostrarLibros(libros);
        })
        .catch(error => {
            console.error('Error:', error);
            mostrarError(`Error al cargar los libros: ${error.message}`);
        });
}

// Función para cargar libros por idioma
function cargarLibrosPorIdioma(idioma) {
    mostrarCargando();
    
    fetch(`${API_URL}/libros/idioma/${idioma}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los libros');
            }
            return response.json();
        })
        .then(libros => {
            mostrarLibros(libros);
        })
        .catch(error => {
            console.error('Error:', error);
            mostrarError(`Error al cargar los libros: ${error.message}`);
        });
}

// Función para mostrar libros en la interfaz
function mostrarLibros(libros) {
    if (libros.length === 0) {
        librosContainer.innerHTML = '<div class="col-12 text-center">No se encontraron libros</div>';
        return;
    }
    
    let html = '';
    libros.forEach(libro => {
        const portadaUrl = libro.urlPortada || 'https://via.placeholder.com/150x220?text=Sin+Portada';
        html += `
        <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
            <div class="card libro-card">
                <div class="card-img-container">
                    <img src="${portadaUrl}" class="card-img-top" alt="${libro.titulo}">
                </div>
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title book-title">${libro.titulo}</h5>
                    <p class="card-text book-author">${libro.autor ? libro.autor.nombre : 'Autor desconocido'}</p>
                    <a href="pages/detalle.html?id=${libro.id}" class="btn btn-primary mt-auto">Ver detalles</a>
                </div>
            </div>
        </div>
        `;
    });
    
    librosContainer.innerHTML = html;
}

// Funciones auxiliares
function mostrarCargando() {
    librosContainer.innerHTML = '<div class="col-12 text-center"><div class="spinner-border text-primary" role="status"><span class="visually-hidden">Cargando...</span></div></div>';
}

function mostrarError(mensaje) {
    librosContainer.innerHTML = `<div class="col-12 text-center text-danger">${mensaje}</div>`;
}