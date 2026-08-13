import java.util.ArrayList;

// ATRIBUTOS
public class Consumidor {
    private int codigo;
    private double consumo;
    private int baixaRenda;
    private int bandeira;


    // CONSTRUTOR
    public Consumidor(int codigo, double consumo, int baixaRenda, int bandeira) {
        this.setCodigo(codigo);
        this.setConsumo(consumo);
        this.setBaixaRenda(baixaRenda);
        this.setBandeira(bandeira);
    }

    // GETTERS
    public int getCodigo() {return this.codigo;}
    public double getConsumo() {return this.consumo;}
    public int getBaixaRenda() {return this.baixaRenda;}
    public int getBandeira() {return this.bandeira;}

    // SETTERS
    public void setCodigo(int codigo) {
        if (codigo < 0){
            System.out.println("Código inválido");
            return;
        }
        this.codigo = codigo;
    }
    public void setConsumo(double consumo) {
        if (consumo < 0){
            System.out.println("Consumo inválido");
            return;
        }
        this.consumo = consumo;
    }
    public void setBaixaRenda(int baixaRenda) {
        if (baixaRenda != 1 && baixaRenda != 0){
            System.out.println("Entrada para Baixa Renda inválida!");
            return;
        }
        this.baixaRenda = baixaRenda;
    }
    public void setBandeira(int bandeira){
        if (bandeira != 1 && bandeira != 2 && bandeira != 3){
            System.out.println("Entrada para tipo de bandeira inválida!");
            return;
        }
        this.bandeira = bandeira;
    }

    // MÉTODOS
    public double calcularConta()
    {
        double valorFinal;
        if (this.consumo <= 100) {valorFinal = (this.consumo * 0.6);}

        else if (this.consumo <=200) {valorFinal = (100 * 0.6) + ((this.consumo - 100) * 0.75);}

        else {valorFinal = (100 * 0.75) + (100 * 0.6) + ((this.consumo - 200) * 0.9);}

        if (this.bandeira == 2) {valorFinal = valorFinal + (this.consumo*0.05);}

        else if (this.bandeira == 3) {valorFinal = valorFinal + (this.consumo*0.1) ;}

        if (this.baixaRenda == 1) {
            if (this.consumo <= 150) {valorFinal = valorFinal - (0.1 * valorFinal);}
        }

        return valorFinal;
    }

}
