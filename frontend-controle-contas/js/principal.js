document.addEventListener('DOMContentLoaded', function() {
    const addContaForm = document.getElementById('add-conta-form');
    const contasTableBody = document.getElementById('contas-table').querySelector('tbody');
    const categoriaSelect = document.getElementById('categoriaId');

    // Função para carregar as categorias dinamicamente
    function carregarCategorias() {
        fetch('http://localhost:8080/categorias')
            .then(response => response.json())
            .then(categorias => {
                categorias.forEach(categoria => {
                    const option = document.createElement('option');
                    option.value = categoria.id;
                    option.text = categoria.descricao;
                    categoriaSelect.add(option);
                });
            })
            .catch(error => console.error('Erro ao carregar categorias:', error));
    }

    // Função para adicionar uma nova conta
    function adicionarConta(event) {
        event.preventDefault();

        const descricao = document.getElementById('descricao').value;
        const valor = parseFloat(document.getElementById('valor').value);
        const dataVencimento = document.getElementById('dataVencimento').value;
        const tipoConta = document.getElementById('tipoConta').value;
        const categoriaId = parseInt(document.getElementById('categoriaId').value);
        const status = document.getElementById('status').value === 'true';

        // Obtém o ID do usuário do localStorage (você precisará armazená-lo após o login)
        const usuarioId = localStorage.getItem('usuarioId');

        const conta = {
            descricao: descricao,
            valor: valor,
            dataVencimento: dataVencimento,
            tipoConta: tipoConta,
            status: status
        };

        fetch(`http://localhost:8080/contas?usuarioId=${usuarioId}&categoriaId=${categoriaId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(conta)
        })
        .then(response => {
            if (response.status === 201) {
                alert('Conta adicionada com sucesso!');
                carregarContas(); // Recarrega as contas após adicionar
                addContaForm.reset(); // Limpa o formulário
            } else {
                alert('Erro ao adicionar conta. Verifique o console para mais detalhes.');
            }
        })
        .catch(error => console.error('Erro ao adicionar conta:', error));
    }

    // Função para carregar as contas cadastradas
    function carregarContas() {
        contasTableBody.innerHTML = ''; // Limpa a tabela antes de carregar

        fetch('http://localhost:8080/contas')
            .then(response => response.json())
            .then(contas => {
                contas.forEach(conta => {
                    const row = contasTableBody.insertRow();
                    row.insertCell().text = conta.descricao;
                    row.insertCell().text = conta.valor;
                    row.insertCell().text = conta.dataVencimento;
                    row.insertCell().text = conta.tipoConta;
                    row.insertCell().text = conta.categoria ? conta.categoria.descricao : 'N/A';
                    row.insertCell().text = conta.status ? 'Paga' : 'Pendente';

                    // Adicionei um botão para excluir a conta
                    const deleteButton = document.createElement('button');
                    deleteButton.text = 'Excluir';
                    deleteButton.addEventListener('click', () => excluirConta(conta.id));
                    row.insertCell().appendChild(deleteButton);
                });
            })
            .catch(error => console.error('Erro ao carregar contas:', error));
    }

    // Função para excluir uma conta
    function excluirConta(id) {
        if (confirm('Tem certeza que deseja excluir esta conta?')) {
            fetch(`http://localhost:8080/contas/${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.status === 204) {
                    alert('Conta excluída com sucesso!');
                    carregarContas(); // Recarrega as contas após excluir
                } else {
                    alert('Erro ao excluir conta. Verifique o console para mais detalhes.');
                }
            })
            .catch(error => console.error('Erro ao excluir conta:', error));
        }
    }

    // Event listeners
    addContaForm.addEventListener('submit', adicionarConta);

    // Carrega as categorias e as contas ao carregar a página
    carregarCategorias();
    carregarContas();
});
