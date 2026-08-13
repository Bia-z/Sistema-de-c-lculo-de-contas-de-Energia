import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class SistemaEnergiaGUI extends JFrame {
    private GerenciadorConsumidores sistema;
    private JTextArea areaDisplay;
    private JTextField txtCodigo, txtConsumo;
    private JComboBox<String> cbBandeira;
    private JCheckBox chkBaixaRenda;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public SistemaEnergiaGUI() {
        sistema = new GerenciadorConsumidores();

        setTitle("Sistema de Gestão de Energia");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usar um layout bonito
        setLayout(new BorderLayout(10, 10));

        // ===== PAINEL SUPERIOR (Título) =====
        JLabel titulo = new JLabel("SISTEMA DE CONTAS DE ENERGIA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 100, 200));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // ===== PAINEL CENTRAL (com abas) =====
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("📋 Cadastro", criarPainelCadastro());
        abas.addTab("🔍 Consultar", criarPainelConsulta());
        abas.addTab("📊 Listar Todos", criarPainelListagem());
        abas.addTab("📈 Relatórios", criarPainelRelatorios());
        abas.addTab("⚙️ Atualizar/Apagar", criarPainelGerenciamento());

        add(abas, BorderLayout.CENTER);

        // ===== PAINEL INFERIOR (Status) =====
        JLabel status = new JLabel("Sistema pronto para uso | Total de consumidores: 0");
        status.setBorder(BorderFactory.createEtchedBorder());
        add(status, BorderLayout.SOUTH);
    }

    // ===== PAINEL DE CADASTRO =====
    private JPanel criarPainelCadastro() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Formulário
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Dados do Consumidor"));

        form.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        form.add(txtCodigo);

        form.add(new JLabel("Consumo (kWh):"));
        txtConsumo = new JTextField();
        form.add(txtConsumo);

        form.add(new JLabel("Bandeira:"));
        cbBandeira = new JComboBox<>(new String[]{"Verde", "Amarela", "Vermelha"});
        form.add(cbBandeira);

        form.add(new JLabel("Baixa Renda:"));
        chkBaixaRenda = new JCheckBox("Sim");
        form.add(chkBaixaRenda);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setBackground(new Color(100, 150, 0));
        btnCadastrar.setForeground(Color.RED);
        form.add(btnCadastrar);

        // Área de exibição
        areaDisplay = new JTextArea();
        areaDisplay.setEditable(false);
        areaDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaDisplay);
        scroll.setBorder(BorderFactory.createTitledBorder("Últimas ações"));
        scroll.setPreferredSize(new Dimension(400, 200));

        painel.add(form, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        // Ação do botão
        btnCadastrar.addActionListener(e -> cadastrarConsumidor());

        return painel;
    }

    // ===== PAINEL DE CONSULTA =====
    private JPanel criarPainelConsulta() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topo = new JPanel(new FlowLayout());
        topo.add(new JLabel("Código do consumidor:"));
        JTextField txtBusca = new JTextField(10);
        topo.add(txtBusca);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(0, 100, 200));
        btnBuscar.setForeground(Color.RED);
        topo.add(btnBuscar);

        painel.add(topo, BorderLayout.NORTH);

        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);
        resultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(resultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Dados do Consumidor"));
        painel.add(scroll, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtBusca.getText());
                Consumidor c = buscarConsumidor(codigo);
                if (c != null) {
                    resultado.setText(formatarDadosConsumidor(c));
                } else {
                    resultado.setText("Consumidor não encontrado!");
                }
            } catch (NumberFormatException ex) {
                resultado.setText("Digite um código numérico válido!");
            }
        });

        return painel;
    }

    // ===== PAINEL DE LISTAGEM =====
    private JPanel criarPainelListagem() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela
        String[] colunas = {"Código", "Consumo (kWh)", "Bandeira", "Baixa Renda", "Valor Conta (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Consumidores"));

        JButton btnAtualizarLista = new JButton("Atualizar Lista");
        btnAtualizarLista.addActionListener(e -> atualizarTabela());

        painel.add(scroll, BorderLayout.CENTER);
        painel.add(btnAtualizarLista, BorderLayout.SOUTH);

        return painel;
    }

    // ===== PAINEL DE RELATÓRIOS =====
    private JPanel criarPainelRelatorios() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea relatorioArea = new JTextArea();
        relatorioArea.setEditable(false);
        relatorioArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(relatorioArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Relatório Geral"));

        JButton btnGerar = new JButton("GERAR RELATÓRIO");
        btnGerar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGerar.setBackground(new Color(0, 150, 200));
        btnGerar.setForeground(Color.RED);

        btnGerar.addActionListener(e -> {
            if (sistema.getConsumidores().isEmpty()) {
                relatorioArea.setText("Nenhum consumidor cadastrado!");
                return;
            }
            Relatorio relatorio = new Relatorio(sistema.getConsumidores());
            relatorioArea.setText(formatarRelatorio(relatorio));
        });

        painel.add(btnGerar, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    // ===== PAINEL DE GERENCIAMENTO =====
    private JPanel criarPainelGerenciamento() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topo = new JPanel(new FlowLayout());
        topo.add(new JLabel("Código:"));
        JTextField txtCodGerenciar = new JTextField(10);
        topo.add(txtCodGerenciar);

        JButton btnApagar = new JButton("Apagar");
        btnApagar.setBackground(Color.WHITE);
        btnApagar.setForeground(Color.RED);
        topo.add(btnApagar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(new Color(255, 165, 0));
        btnAtualizar.setForeground(Color.RED);
        topo.add(btnAtualizar);

        painel.add(topo, BorderLayout.NORTH);

        JTextArea resultadoGerenciamento = new JTextArea();
        resultadoGerenciamento.setEditable(false);
        resultadoGerenciamento.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(resultadoGerenciamento);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));
        painel.add(scroll, BorderLayout.CENTER);

        btnApagar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodGerenciar.getText());
                boolean apagou = apagarConsumidor(codigo);
                if (apagou) {
                    resultadoGerenciamento.setText("Consumidor " + codigo + " apagado com sucesso!");
                    atualizarTabela();
                } else {
                    resultadoGerenciamento.setText("Consumidor não encontrado!");
                }
            } catch (NumberFormatException ex) {
                resultadoGerenciamento.setText("Digite um código válido!");
            }
        });

        btnAtualizar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodGerenciar.getText());
                Consumidor c = buscarConsumidor(codigo);
                if (c != null) {
                    abrirJanelaAtualizacao(c);
                    atualizarTabela();
                } else {
                    resultadoGerenciamento.setText("Consumidor não encontrado!");
                }
            } catch (NumberFormatException ex) {
                resultadoGerenciamento.setText("Digite um código válido!");
            }
        });

        return painel;
    }

    // ===== MÉTODOS AUXILIARES =====

    private void cadastrarConsumidor() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText());
            double consumo = Double.parseDouble(txtConsumo.getText());
            int bandeira = cbBandeira.getSelectedIndex() + 1;
            int baixaRenda = chkBaixaRenda.isSelected() ? 1 : 0;

            Consumidor c = new Consumidor(codigo, consumo, baixaRenda, bandeira);
            sistema.getConsumidores().add(c);

            areaDisplay.append("Consumidor " + codigo + " cadastrado com sucesso!\n");
            areaDisplay.append("Consumo: " + consumo + " kWh | Bandeira: " + cbBandeira.getSelectedItem() + "\n\n");

            // Limpar campos
            txtCodigo.setText("");
            txtConsumo.setText("");
            chkBaixaRenda.setSelected(false);

            atualizarTabela();
            atualizarStatus();

        } catch (NumberFormatException ex) {
            areaDisplay.append("Erro: Digite valores numéricos válidos!\n\n");
        }
    }

    private Consumidor buscarConsumidor(int codigo) {
        for (Consumidor c : sistema.getConsumidores()) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }

    private boolean apagarConsumidor(int codigo) {
        for (int i = 0; i < sistema.getConsumidores().size(); i++) {
            if (sistema.getConsumidores().get(i).getCodigo() == codigo) {
                sistema.getConsumidores().remove(i);
                return true;
            }
        }
        return false;
    }

    private void abrirJanelaAtualizacao(Consumidor c) {
        JDialog dialog = new JDialog(this, "Atualizar Consumidor - Código " + c.getCodigo(), true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        dialog.add(new JLabel("Novo Consumo (kWh):"));
        JTextField txtNovoConsumo = new JTextField(String.valueOf(c.getConsumo()));
        dialog.add(txtNovoConsumo);

        dialog.add(new JLabel("Nova Bandeira:"));
        JComboBox<String> cbNovaBandeira = new JComboBox<>(new String[]{"Verde", "Amarela", "Vermelha"});
        cbNovaBandeira.setSelectedIndex(c.getBandeira() - 1);
        dialog.add(cbNovaBandeira);

        dialog.add(new JLabel("Baixa Renda:"));
        JCheckBox chkNovaBaixaRenda = new JCheckBox("Sim", c.getBaixaRenda() == 1);
        dialog.add(chkNovaBaixaRenda);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        dialog.add(btnSalvar);
        dialog.add(btnCancelar);

        btnSalvar.addActionListener(e -> {
            try {
                c.setConsumo(Double.parseDouble(txtNovoConsumo.getText()));
                c.setBandeira(cbNovaBandeira.getSelectedIndex() + 1);
                c.setBaixaRenda(chkNovaBaixaRenda.isSelected() ? 1 : 0);
                JOptionPane.showMessageDialog(dialog, "Dados atualizados com sucesso!");
                dialog.dispose();
                atualizarTabela();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Digite um valor numérico válido!");
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Consumidor c : sistema.getConsumidores()) {
            String bandeira = "";
            if (c.getBandeira() == 1) bandeira = "Verde";
            else if (c.getBandeira() == 2) bandeira = "Amarela";
            else bandeira = "Vermelha";

            String baixaRenda = c.getBaixaRenda() == 1 ? "Sim" : "Não";
            double valorConta = c.calcularConta();

            modeloTabela.addRow(new Object[]{
                    c.getCodigo(),
                    String.format("%.2f", c.getConsumo()),
                    bandeira,
                    baixaRenda,
                    String.format("%.2f", valorConta)
            });
        }
        atualizarStatus();
    }

    private void atualizarStatus() {
        JLabel status = (JLabel) ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        status.setText("Sistema pronto | Total de consumidores: " + sistema.getConsumidores().size());
    }

    private String formatarDadosConsumidor(Consumidor c) {
        String bandeira = "";
        if (c.getBandeira() == 1) bandeira = "Verde";
        else if (c.getBandeira() == 2) bandeira = "Amarela";
        else bandeira = "Vermelha";

        return "═══════════════════════════════════════\n" +
                "         DADOS DO CONSUMIDOR          \n" +
                "═══════════════════════════════════════\n" +
                "Código          : " + c.getCodigo() + "\n" +
                "Consumo         : " + String.format("%.2f", c.getConsumo()) + " kWh\n" +
                "Bandeira        : " + bandeira + "\n" +
                "Baixa Renda     : " + (c.getBaixaRenda() == 1 ? "Sim" : "Não") + "\n" +
                "Valor da Conta  : R$ " + String.format("%.2f", c.calcularConta()) + "\n" +
                "═══════════════════════════════════════\n";
    }

    private String formatarRelatorio(Relatorio relatorio) {
        return "═══════════════════════════════════════════════════════════\n" +
                "                   RELATÓRIO GERAL                        \n" +
                "═══════════════════════════════════════════════════════════\n" +
                "Consumo Total          : " + String.format("%.2f", relatorio.consumoTotal()) + " kWh\n" +
                "Consumo Médio          : " + String.format("%.2f", relatorio.consumoMedio()) + " kWh\n" +
                "Valor Total das Contas : R$ " + String.format("%.2f", relatorio.valorTotal()) + "\n" +
                "Consumidores +200kWh   : " + relatorio.mais200() + " consumidores\n" +
                "Percentual +200kWh     : " + String.format("%.2f", relatorio.percentualMais200()) + "%\n" +
                "Maior Conta            : Consumidor " + relatorio.maiorConta() + "\n" +
                "Valor da Maior Conta   : R$ " + String.format("%.2f", relatorio.valorMaiorConta()) + "\n" +
                "═══════════════════════════════════════════════════════════\n" +
                "Total de consumidores  : " + sistema.getConsumidores().size() + "\n" +
                "═══════════════════════════════════════════════════════════\n";
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        // Usar o Look and Feel do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new SistemaEnergiaGUI().setVisible(true);
        });
    }
}