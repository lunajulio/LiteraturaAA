
const API_URL = 'http://localhost:8080/api';

// Elementos del DOM
const detalleContainer = document.getElementById('detalleContainer');

// Cargar detalle del libro
document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const libroId = urlParams.get('id');
    
    if (libroId) {
        cargarDetalleLibro(libroId);
    } else {
        mostrarError('No se especificó un ID de libro');
    }
});

// Función para cargar el detalle de un libro
function cargarDetalleLibro(id) {
    fetch(`${API_URL}/libros/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el detalle del libro');
            }
            return response.json();
        })
        .then(libro => {
            mostrarDetalleLibro(libro);
        })
        .catch(error => {
            console.error('Error:', error);
            mostrarError(`Error al cargar el detalle: ${error.message}`);
        });
}

// Función para mostrar el detalle del libro
function mostrarDetalleLibro(libro) {
    const portadaUrl = libro.urlPortada || 'https://via.placeholder.com/300x450?text=Sin+Portada';
    
    let idiomas = '';
    if (libro.idioma) {
        const idiomasCompletos = {
            'es': 'Español',
            'en': 'Inglés',
            'fr': 'Francés',
            'it': 'Italiano',
            'de': 'Alemán',
            'pt': 'Portugués'
        };
        idiomas = libro.idioma.split(',').map(i => idiomasCompletos[i.trim()] || i.trim()).join(', ');
    }
    
    const html = `
    <div class="col-md-4 mb-4">
        <img src="${portadaUrl}" class="img-fluid rounded book-detail-img" alt="${libro.titulo}">
    </div>
    <div class="col-md-8">
        <h1 class="book-detail-title">${libro.titulo}</h1>
        
        <div class="info-section">
            <h5>Autor</h5>
            <p class="fs-5">${libro.autor ? libro.autor.nombre : 'Autor desconocido'}</p>
            
            ${libro.autor && (libro.autor.fechaDeNacimiento || libro.autor.fechaDeFallecimiento) ? `
            <h5 class="mt-4">Fechas</h5>
            <p>
                ${libro.autor.fechaDeNacimiento ? `<strong>Nacimiento:</strong> ${libro.autor.fechaDeNacimiento}` : ''}
                ${libro.autor.fechaDeFallecimiento ? `<br><strong>Fallecimiento:</strong> ${libro.autor.fechaDeFallecimiento}` : ''}
            </p>
            ` : ''}
        </div>
        
        <div class="info-section">
            ${idiomas ? `
            <h5>Idiomas</h5>
            <p>${idiomas}</p>
            ` : ''}
            
            ${libro.descargas ? `
            <h5>Estadísticas</h5>
            <p><strong>Descargas:</strong> ${libro.descargas.toLocaleString()}</p>
            ` : ''}
        </div>
        
        <div class="mt-4">
            <a href="../index.html" class="btn btn-secondary">Volver a la biblioteca</a>
        </div>
    </div>
    `;
    
    detalleContainer.innerHTML = html;
    document.title = `${libro.titulo} - LiteraturA`;
}

// Funciones auxiliares
function mostrarError(mensaje) {
    detalleContainer.innerHTML = `<div class="col-12 text-center text-danger">${mensaje}</div>`;
}