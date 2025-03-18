document.getElementById('cadastro-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const cpf = document.getElementById('cpf').value;
    const telefone = document.getElementById('telefone').value;
    const endereco = document.getElementById('endereco').value;
    const email = document.getElementById('email').value;
    const login = document.getElementById('login').value;
    const senha = document.getElementById('senha').value;

    const usuario = {
        nome: nome,
        cpf: cpf,
        telefone: telefone,
        endereco: endereco,
        email: email,
        login: login,
        senha: senha
    };

    fetch('http://localhost:8080/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuario)
    })
    .then(response => {
        if (response.status === 201) {
            // Cadastro bem-sucedido
            alert('Cadastro realizado com sucesso!');
            // Redirecionar para a página de login
            window.location.href = 'login.html';
        } else if (response.status === 400) {
            // Cadastro falhou (erro no backend)
            response.json().then(data => {
                if (data && data.message && data.message.includes('Duplicate entry')) {
                    alert('CPF já cadastrado. Por favor, use um CPF diferente.');
                } else {
                    alert('Erro ao cadastrar usuário: ' + (data && data.message ? data.message : 'Erro desconhecido'));
                }
            });
        } else {
            // Outros erros
            alert('Erro ao cadastrar usuário. Verifique o console para mais detalhes.');
        }
    })
    .catch(error => {
        console.error('Erro ao cadastrar usuário:', error);
        alert('Erro ao cadastrar usuário. Verifique o console para mais detalhes.');
    });
});
