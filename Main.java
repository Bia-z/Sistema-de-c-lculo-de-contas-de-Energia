import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Sistema de cálculo de contas de energia.");
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        Cadastro sistema = new Cadastro();
        Relatorio relatorio = new Relatorio(sistema.getConsumidores());


        int opcao = 0;
        while (opcao != 7) {
            menu();
            opcao = Integer.parseInt(teclado.readLine());

            if (opcao == 1) {
                sistema.cadastrarConsumidor(teclado);
            }
            else if (opcao == 2) {
                sistema.consultarConsumidor(teclado);
            }
            else if (opcao == 3) {
                System.out.println("Opção não implementada.");
            }
            else if (opcao == 4) {
                System.out.println("Opção não implementada.");
            }
            else if (opcao == 5) {
                relatorio.imprimirRelatorio();
            }
            else if (opcao == 6) {
                System.out.println("Opção não implementada.");
            }
            else if (opcao == 7) {
                System.out.println("Encerrando o programa...");
            }
        }

    }

    public static void menu() {
        System.out.printf("MENU\n");
        System.out.printf("1. Cadastrar novo consumidor.\n");
        System.out.printf("2. Consultar dados de um consumidor específico.\n");
        System.out.printf("3. Apagar um consumidor.\n");
        System.out.printf("4. Atualizar dados de um consumidor.\n");
        System.out.printf("5. Relatórios.\n");
        System.out.printf("6. Listar consumidores.\n");
        System.out.printf("7. Sair do programga.\n");
    }
}