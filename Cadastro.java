import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Cadastro {
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
        System.out.println("Insira o código numérico do consumidor: \n");
        int codigo = Integer.parseInt(teclado.readLine());

        System.out.println("Insira o consumo mensal em kWh: \n");
        double consumo = Double.parseDouble(teclado.readLine());

        System.out.println("Qual a cor da bandeira?");
        System.out.printf("1 - Verde\n2-Amarela\n3-Vermelha\n");
        int bandeira = Integer.parseInt(teclado.readLine());

        System.out.printf("Participa do programa de baixa renda?\n0-Não\n1-Sim\n");
        int baixaRenda = Integer.parseInt(teclado.readLine());

        Consumidor novo = new Consumidor(codigo, consumo, baixaRenda, bandeira); //
        //Consumidor aqui é o tipo da váriavel novo
        // mesma ordem do construtor
        this.consumidores.add(novo);


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