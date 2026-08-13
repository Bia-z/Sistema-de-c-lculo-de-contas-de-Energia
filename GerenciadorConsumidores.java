import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class GerenciadorConsumidores {
    // ATRIBUTOS
    private ArrayList<Consumidor> consumidores;

    // CONSTRUTOR
    public GerenciadorConsumidores() {
        this.consumidores = new ArrayList<>();
    }

    //GETTERS
    public ArrayList<Consumidor> getConsumidores() {
        return this.consumidores;
    }

    // MÉTODOS
    public void cadastrarConsumidor(BufferedReader teclado) throws IOException {
        int codigo = -1;
        boolean codigoValido = false;

        while (!codigoValido) {
            System.out.println("Insira o código numérico do consumidor: \n");
            try {
                codigo = Integer.parseInt(teclado.readLine());
                if (codigo <= 0) {
                    System.out.println("Código inválido! Digite novamente.");
                } else {
                    codigoValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digitar apenas numerais válidos.");
            }
        }

        double consumo = -1;
        boolean consumoValido = false;

        while (!consumoValido) {
            System.out.println("Insira o consumo mensal em kWh: \n");
            try {
                consumo = Double.parseDouble(teclado.readLine());
                if (consumo < 0) {
                    System.out.println("Consumo inválido! Digite novamente.");
                } else {
                    consumoValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digitar penas numerais válidos.");
            }
        }

        int bandeira = -1;
        boolean bandeiraValida = false;
        while (!bandeiraValida) {
            System.out.println("Qual a cor da bandeira?");
            System.out.printf("1 - Verde\n2-Amarela\n3-Vermelha\n");
            try {
                bandeira = Integer.parseInt(teclado.readLine());
                if (bandeira != 1 && bandeira != 2 && bandeira != 3) {
                    System.out.println("Digite apenas numerais de 1 à 3.");
                } else {
                    bandeiraValida = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digitar apenas numerais válidos.");
            }
        }

        int baixaRenda = -1;
        boolean baixaRendaValida = false;
        while (!baixaRendaValida) {
            System.out.printf("Participa do programa de baixa renda?\n0-Não\n1-Sim\n");
            try {
                baixaRenda = Integer.parseInt(teclado.readLine());
                if (baixaRenda != 0 && baixaRenda != 1) {
                    System.out.println("Digitar apenas numerais entre 0 e 1.");
                } else {
                    baixaRendaValida = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digitar apenas numerais válidos.");
            }
        }

        Consumidor novo = new Consumidor(codigo, consumo, baixaRenda, bandeira); //
        //Consumidor aqui é o tipo da váriavel novo
        // mesma ordem do construtor
        this.consumidores.add(novo);

    }

    public void consultarConsumidor(BufferedReader teclado) throws IOException {
        int codBuscado = -1;
        boolean valido = false;
        while (!valido) {
            System.out.println("Digite o código numérico do consumidor: \n");
            try {
                codBuscado = Integer.parseInt(teclado.readLine());
                if (codBuscado <= 0) {
                    System.out.println("Digitar apenas numerais acima de 0");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digitar apenas numerais válidos.");
            }
        }
        boolean achou = false;
        for (int i = 0; this.consumidores.size() > i; i++) {
            Consumidor c = this.consumidores.get(i);
            if (c.getCodigo() == codBuscado) {
                achou = true;
                System.out.println("Código do consumidor: " + c.getCodigo());
                System.out.println("Consumo em kWh: " + c.getConsumo());
                System.out.println("Bandeira: " + c.getBandeira());
                if (c.getBaixaRenda() == 1) {
                    System.out.println("Baixa renda: Sim");
                } else {
                    System.out.println("Baixa renda: Não");
                }
                double valorFinal = c.calcularConta();
                System.out.printf("Valor final da conta: %.2f\n", valorFinal);
            }
        }
        if (!achou) {
            System.out.println("Consumidor não encontrado.");
        }
    }

    public Relatorio gerarRelatorio() {
        return new Relatorio(this.consumidores);
    }

    public void listarConsumidores() {
        System.out.println("Consumidores cadastrados: ");
        for (int i = 0; i < this.consumidores.size(); i++) {
            Consumidor c = this.consumidores.get(i);
            System.out.printf("%d. Código: %d\n", (i+1), c.getCodigo());
        }
    }

    public int encontrarConsumidor(BufferedReader teclado) throws IOException {
        listarConsumidores();

        System.out.println("Digite o código do consumidor que deseja:");

        try {
            int cod = Integer.parseInt(teclado.readLine());

            if (cod <= 0) {
                System.out.println("Digite apenas valores acima de 0.");
                return 0;
            }

            for (int i = 0; i < this.consumidores.size(); i++) {
                Consumidor c = this.consumidores.get(i);

                if (c.getCodigo() == cod) {
                    return cod;
                }
            }

            return 0;

        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            return 0;
        }
    }

    public void apagarConsumidor(BufferedReader teclado) throws IOException {
        int cod = encontrarConsumidor(teclado);

        if (cod == 0) {
            System.out.println("Consumidor não encontrado.");
            return;
        }

        for (int i = 0; i < this.consumidores.size(); i++) {
            if (this.consumidores.get(i).getCodigo() == cod) {
                this.consumidores.remove(i);
                System.out.println("Consumidor apagado com sucesso!");
                return;
            }
        }
    }

    public void atualizarDados(BufferedReader teclado) throws IOException {
        int cod = encontrarConsumidor(teclado);

        if (cod == 0) {
            System.out.println("Consumidor não encontrado.");
            return;
        }

        listarConsumidorEspecifico(cod);

        for (int i = 0; i < this.consumidores.size(); i++) {
            Consumidor c = this.consumidores.get(i);
            if (c.getCodigo() == cod) {
                System.out.printf("Consumidor encontrado! Qual informação deseja atualizar?" +
                        "\n 1. Código do consumidor." +
                        "\n 2. Consumo." +
                        "\n 3. Pariticipação do programa baixa renda." +
                        "\n 4. Bandeira da conta.\n");
                try {
                    int opcao = Integer.parseInt(teclado.readLine());

                    if (opcao == 1) {
                        System.out.println("Qual o novo código? ");
                        int novoCod = Integer.parseInt(teclado.readLine());
                        c.setCodigo(novoCod);
                        System.out.println("Código atualizado com sucesso!");

                    } else if (opcao == 2) {
                        System.out.println("Digite o novo consumo: ");
                        int novoConsumo = Integer.parseInt(teclado.readLine());
                        c.setConsumo(novoConsumo);
                        System.out.println("Consumo atualizado com sucesso!");

                    } else if (opcao == 3) {
                        System.out.print("Participa do programa baixa renda? (0-Não / 1-Sim): ");
                        int nBaixaRenda = Integer.parseInt(teclado.readLine());
                        c.setBaixaRenda(nBaixaRenda);
                        System.out.println("Status de baixa renda atualizado com sucesso!");

                    } else if (opcao == 4) {
                        System.out.println("Bandeira: (1-Verde / 2-Amarela / 3-Vermelha)");
                        int novaBandeira = Integer.parseInt(teclado.readLine());
                        c.setBandeira(novaBandeira);
                        System.out.println("Bandeira atualizada com sucesso!");

                    } else {
                        System.out.println("Escolha uma opção válida!");
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("Digite apenas números.");
                }
                break;
            }
        }
    }

    public void listarConsumidorEspecifico(int codigo) {
        boolean encontrado = false;

        for (int i =0; i < this.consumidores.size(); i++) {
            Consumidor c = this.consumidores.get(i);

            if (c.getCodigo() == codigo) {
                encontrado = true;

                //vou converter a bandeira de numero pra nome, e baixa renda tb
                String bandeira;
                if (c.getBandeira() == 1) {
                    bandeira = "Verde";
                } else if (c.getBandeira() == 2) {
                    bandeira = "Amarela";
                } else {
                    bandeira = "Vermelha";
                }

                String baixaRendastr;
                if (c.getBaixaRenda() == 1) {
                    baixaRendastr = "Sim";
                } else {
                    baixaRendastr = "Não";
                }

                System.out.printf("1. Código: %d" +
                                "\n 2. Consumo: %.2f kWh" +
                                "\n 3. Participa do programa de baixa renda? %s" +
                                "\n 4. Bandeira da conta: %s\n",
                        c.getCodigo(), c.getConsumo(), baixaRendastr, bandeira);

                break;
            }
        }
        if (!encontrado) {
            System.out.println("Consumidor não encontrado.");
        }
    }
}