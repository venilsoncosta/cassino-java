import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int balance = 100; // Saldo inicial
        String depositChoice;

        System.out.println("----------------------------------------------");
        System.out.println("*** Bem vindo ao cassino mais justo ***");
        System.out.println("Símbolos: 😢 😂 ❤️ 🌹 🤑 ");
        System.out.println("----------------------------------------------");


        while (true) {

            if (balance <= 0) {
                System.out.println("Seu saldo acabou. Deseja realizar um depósito para continuar jogando? (s/n) ");
                depositChoice = scanner.next();
                if (depositChoice.equalsIgnoreCase("s")) {
                    balance = deposit(balance, scanner);
                    if (balance <= 0) {
                        System.out.println("Depósito insuficiente para continuar. Encerrando o jogo.");
                        break;
                    }
                } else {
                    System.out.println("Obrigado por jogar! Game Over.");
                    break;
                }
            }


            int bet;
            int payout;
            String[] row;

            System.out.println("Saldo atual: R$" + balance);
            System.out.print("Qual valor deseja apostar? ");


            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro para a aposta.");
                scanner.next();
                System.out.print("Qual valor deseja apostar? ");
            }
            bet = scanner.nextInt();

            if (bet > balance) {
                System.out.println("Saldo insuficiente para apostar. Tente um valor menor ou faça um depósito.");

                continue;
            } else if (bet <= 0) {
                System.out.println("Aposta mínima é R$1,00.");
                continue;
            } else {
                balance -= bet;
            }

            System.out.println("Rodando....");
            row = spinRow();
            printRow(row);
            payout = getPayout(row, bet);

            if (payout > 0) {
                System.out.println("Você ganhou R$" + payout);
                balance += payout;
            } else {
                System.out.println("Você perdeu nessa rodada, amigo.");
            }
        }

        scanner.close();
    }


    static String[] spinRow() {
        String[] simbolos = {"😢", "😂", "❤️", "🌹", "🤑"};
        String[] row = new String[3];
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            row[i] = simbolos[random.nextInt(0, 5)];
        }
        return row;
    }

    static void printRow(String[] row) {
        System.out.println("******************");
        System.out.println(" " + String.join(" | ", row));
        System.out.println("******************");
    }

    static int getPayout(String[] row, int bet) {
        if (row[0].equals(row[1]) && row[1].equals(row[2])) {
            return switch (row[0]) {
                case "😢" -> bet * 2;
                case "😂" -> bet * 3;
                case "❤️" -> bet * 4;
                case "🌹" -> bet * 5;
                case "🤑" -> bet * 10;
                default -> 0;
            };
        }
        return 0;
    }

    static int deposit(int currentBalance, Scanner scanner) {
        int depositAmount;
        while (true) {
            System.out.print("Qual valor deseja depositar? ");
            if (scanner.hasNextInt()) {
                depositAmount = scanner.nextInt();
                if (depositAmount > 0) {
                    System.out.println("Depósito de R$" + depositAmount + " realizado com sucesso!");
                    return currentBalance + depositAmount;
                } else {
                    System.out.println("O valor do depósito deve ser maior que zero.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.next();
            }
        }
    }
}