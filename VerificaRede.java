import java.util.Scanner;

public class VerificaRede {
    final static Scanner LER = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.print("Digite o IP/Máscara ou 'sair' para encerrar: ");
            String n = LER.nextLine();

            if (n.equalsIgnoreCase("sair")) {
                System.out.println("Encerrando...");
                break;
            }

            calcularRede(n);
            System.out.println();
        }
    }

    public static void calcularRede(String n) {
        n = n.trim().replace(" ", "");

        if (!n.contains("/")) {
            System.out.println("Formato inválido! Use o IP/Máscara.");
            return;
        }

        String[] partes = n.split("/");
        String ipStr = partes[0];
        int mascara = 0;

        try {
            mascara = Integer.parseInt(partes[1]);
            if (mascara < 1 || mascara > 32) {
                System.out.println("Máscara inválida!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Máscara inválida!");
            return;
        }
        String[] octetos = ipStr.split("\\.");
        if (octetos.length != 4) {
            System.out.println("IP inválido!");
            return;
        }

        int[] ip = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                ip[i] = Integer.parseInt(octetos[i]);
                if (ip[i] < 0 || ip[i] > 255) {
                    System.out.println("IP inválido!");
                    return;
                }
            } catch (Exception e) {
                System.out.println("IP inválido!");
                return;
            }
        }

        int bitsHost = 32 - mascara;
        int bloco = (int) Math.pow(2, bitsHost); 

        int rede4 = 0;
        for (int i = 0; i <= 255; i = i + bloco) {
            if (i <= ip[3]) {
                rede4 = i;
            } else {
                break;
            }
        }

        int broadcast4 = rede4 + bloco - 1;

        int[] rede = { ip[0], ip[1], ip[2], rede4 };
        int[] broad = { ip[0], ip[1], ip[2], broadcast4 };

        int[] primeiro = { ip[0], ip[1], ip[2], rede4 + 1 };
        int[] ultimo = { ip[0], ip[1], ip[2], broadcast4 - 1 };

        System.out.println("Rede: " + imprimir(rede));
        System.out.println("Broadcast: " + imprimir(broad));
        System.out.println("Hosts: de " + imprimir(primeiro) + " até " + imprimir(ultimo));
    }

    public static String imprimir(int[] ip) {
        return ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
    }
}