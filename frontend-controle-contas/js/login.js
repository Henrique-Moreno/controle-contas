document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const login = document.getElementById('login').value;
    const senha = document.getElementById('senha').value;

    fetch('http://localhost:8080/usuarios/login?login=' + login + '&senha=' + senha, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
    })
    .then(response => {
        if (response.ok) {
            // Login bem-sucedido
            alert('Login realizado com sucesso!');
            // Redirecionar para a página principal (substitua 'principal.html' pelo nome da sua página principal)
            window.location.href = 'principal.html';
        } else {
            // Login falhou
            alert('Login ou senha incorretos.');
        }
    })
    .catch(error => {
        console.error('Erro ao fazer login:', error);
        alert('Erro ao fazer login. Verifique o console para mais detalhes.');
    });
});
