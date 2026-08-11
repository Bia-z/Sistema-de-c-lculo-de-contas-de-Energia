import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Sistema de cálculo de contas de energia.");

        System.out.println("Quantos consumidores serão processados?");
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        int qntdd = Integer.parseInt(teclado.readLine());

        ArrayList<Integer> codigos = new ArrayList<>();
        ArrayList<Double> consumo = new ArrayList<>();
        ArrayList<Integer> renda = new ArrayList<>();
        ArrayList<Integer> bandeira = new ArrayList<>();

        for (int i = 0; i<qntdd; i++){
        System.out.println("Por favor digite o código numérico do " + (i+1) + " consumidor: ");
        codigos.add(Integer.parseInt(teclado.readLine()));

        System.out.println("Digite o consumo mensal do " + (i+1) + " cliente em kWh: ");
        consumo.add(Double.parseDouble(teclado.readLine()));

        System.out.println("O cliente participa do programa de baixa renda?");
        System.out.println("1 - Sim, participa.");
        System.out.println("0 - Não, não participa.");
        renda.add(Integer.parseInt(teclado.readLine()));

        System.out.println("Qual a cor da bandeira da conta?");
        System.out.println("1 - Verde");
        System.out.println("2- Amarela");
        System.out.println("3- Vermelha");
        bandeira.add(Integer.parseInt(teclado.readLine()));

        }

        System.out.println("MENU");
        System.out.println("1. Cadastrar novo consumidor");
        System.out.println("2. Consultar dados de um consumidor específico.");
        System.out.println("3. Apagar um consumidor.");
        System.out.println("4. Atualizar dados de um consumidor.");
        System.out.println("5. Relatórios.");
        System.out.println("6. Listar consumidores.");
        System.out.println("7. Sair do programga.");
        System.out.println("Escolha uma opção: ");
        int opcao = 0;

        while (opcao != 7) {
            menu();
            opcao = Integer.parseInt(teclado.readLine());

            if (opcao == 1) {
                System.out.println("Opção não implementada.");
            }
            else if (opcao == 2) {
                consultarConsumidor();
            }
            else if (opcao == 3) {
                System.out.println("Opção não implementada.");
            }
            else if (opcao == 4) {
                System.out.println("Opção não implementada.");
            }
            else if (opcao == 5) {
                System.out.println("Opção não implementada.");
            }
            else if (opcao == 6) {
                consultarConsumidor();
            }
            else if (opcao == 7) {
                System.out.println("Encerrando o programa...");
            }
        }

    }

    public static void menu() {
        System.out.println("MENU");
        System.out.println("1. Cadastrar novo consumidor");
        System.out.println("2. Consultar dados de um consumidor específico.");
        System.out.println("3. Apagar um consumidor.");
        System.out.println("4. Atualizar dados de um consumidor.");
        System.out.println("5. Relatórios.");
        System.out.println("6. Listar consumidores.");
        System.out.println("7. Sair do programga.");
    }


    public static void consultarConsumidor(BufferedReader teclado, ArrayList<Integer> codigos, ArrayList<Double> consumo, ArrayList<Integer> renda, ArrayList<Integer> bandeira)  throws IOException
    {
        System.out.println("Digite o código numérico do consumidor: ");
        int codBuscado = Integer.parseInt(teclado.readLine());
        for (int i =  0; i<codigos.size(); i++) {
            if (codigos.get(i) == codBuscado) {
                System.out.println("Código do consumidor: " + codigos.get(i));
                System.out.println("Consumo em kWh: " + consumo.get(i));
                System.out.println("Bandeira: " + bandeira.get(i));
                if (renda.get(i) == 1) {
                System.out.println("Baixa renda: Sim"); }
                else {
                    System.out.println("Baixa renda: Não"); }
                double valorFinal = calcularConta(consumo.get(i), bandeira.get(i), renda.get(i));
                System.out.println("Valor final da conta: " + valorFinal);
            }
            else {
                i++;
            }
            //PAREI AQUI
        }

    }

    public static


    public static double calcularConta(double consumo, int bandeira, int baixaRenda)
    {
        double valorFinal;
        if (consumo <= 100) {
            valorFinal = (consumo * 0.6);
        }
        else if (consumo <=200) {
            valorFinal = (100 * 0.6) + ((consumo - 100) * 0.75);
        }
        else {
            valorFinal = (100 * 0.75) + (100 * 0.6) + ((consumo - 200) * 0.9);
        }

        if (bandeira == 2) {
            valorFinal = valorFinal + (consumo*0.05);
        }
        else if (bandeira == 3) {
            valorFinal = valorFinal + (consumo*0.1) ;
        }

        if (baixaRenda == 1) {
            if (consumo <= 150) {
                valorFinal = valorFinal - (0.1 * valorFinal);
            }
        }

        return valorFinal;
    }
}