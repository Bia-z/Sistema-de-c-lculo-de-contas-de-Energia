import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class GerenciadorConsumidores {
    // ATRIBUTOS
    private ArrayList<Consumidor> consumidores;

    // CONSTRUTOR
    public Cadastro() {
        this.consumidores = new ArrayList<>();
    }

    //GETTERS
    public ArrayList<Consumidor> getConsumidores(){
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
            }
                catch (NumberFormatException e) {
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
                    codigoValido = true;
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
                }
                else {
                    bandeiraValida = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Digitar apenas numerais válidos.");
            }
        }

        int baixaRenda = -1;
        boolean baixaRendaValida = false;
        while  (!baixaRendaValida) {
            System.out.printf("Participa do programa de baixa renda?\n0-Não\n1-Sim\n");
            try {
                baixaRenda = Integer.parseInt(teclado.readLine());
                if (baixaRenda != 0 && baixaRenda != 1) {
                    System.out.println("Digitar apenas numerais entre 0 e 1.");
                } else {
                    baixaRendaValida = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Digitar apenas numerais válidos.");
            }
        }

            Consumidor novo = new Consumidor(codigo, consumo, baixaRenda, bandeira); //
            //Consumidor aqui é o tipo da váriavel novo
            // mesma ordem do construtor
            this.consumidores.add(novo);

        }
    }

    public void consultarConsumidor(BufferedReader teclado) throws IOException {
        System.out.println("Digite o código numérico do consumidor: \n");
        int codBuscado = Integer.parseInt(teclado.readLine());

        for (int i = 0; i < this.consumidores.size(); i++) {
            Consumidor c = this.consumidores.get(i);
            if (c.getCodigo() == codBuscado) {
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
    }

    public Relatorio gerarRelatorio() {
        return new Relatorio(this.consumidores);
    }

}