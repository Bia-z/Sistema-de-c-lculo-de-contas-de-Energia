import java.util.ArrayList;

public class Relatorio {
    // ATRIBUTOS
    private ArrayList<Consumidor> consumidores;

    // CONSTRUTOR
    public Relatorio(ArrayList<Consumidor> consumidores) {
        this.consumidores = consumidores;
    }

    // MÉTODOS
    public double consumoTotal()
    {
        double consumoTotal = 0.0;
        for(int i = 0; i < this.consumidores.size(); i++) {
            consumoTotal = consumoTotal + this.consumidores.get(i).getConsumo();
        }
        return consumoTotal;
    }

    public double consumoMedio()
    {
        double consumoMedio = consumoTotal() / this.consumidores.size();
        return consumoMedio;
    }

    public double valorTotal(){
        double valorTotal = 0.0;
        for(int i = 0; i < this.consumidores.size(); i++) {
            valorTotal = valorTotal + this.consumidores.get(i).calcularConta();
        }
        return valorTotal;
    }

    public int mais200(){
        int mais200 = 0;
        for(int i = 0; i < this.consumidores.size(); i++) {
            if (this.consumidores.get(i).getConsumo() > 200) {
                mais200 = mais200 + 1;
            }
        }
        return mais200;
    }

    public double percentualMais200() {
        return (this.mais200() / (double) this.consumidores.size()) * 100;
    }


    public int maiorConta() {
        int codMaiorConta = 0;
        double maiorValor = 0;
        for (int i = 0; i < this.consumidores.size(); i++) {
            double valorAtual = this.consumidores.get(i).calcularConta();
            if (valorAtual > maiorValor) {
                maiorValor = valorAtual;
                codMaiorConta = this.consumidores.get(i).getCodigo();
            }
        }
        return codMaiorConta;
    }

    public double valorMaiorConta() {
        double maiorValor = 0;
        for (int i = 0; i < this.consumidores.size(); i++) {
            double valorAtual = this.consumidores.get(i).calcularConta();
            if (valorAtual > maiorValor) {
                maiorValor = valorAtual;
            }
        }
        return maiorValor;
    }



    public void imprimirRelatorio(){
        System.out.printf("Consumo Total: %.2f" +
                        "\nConsumo médio: %.2f" +
                        "\nValor total das contas: %.2f" +
                        "\nQuantidade de consumidores que usaram +200kWh: %d" +
                        "\nPercentual de consumidores que usaram +200kWh: %.2f%%" +
                        "\nConsumidor com a maior conta: %d" +
                        "\nValor da maior conta: %.2f\n",
                this.consumoTotal(), this.consumoMedio(), this.valorTotal(), this.mais200(), this.percentualMais200(), this.maiorConta(), this.valorMaiorConta());
    }

}