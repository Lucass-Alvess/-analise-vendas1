package application;


import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            List<Sale> sales = new ArrayList<>();

            while(line != null) {
                String[] dados = line.split(",");
                int month = Integer.parseInt(dados[0]);
                int year = Integer.parseInt(dados[1]);
                String seller = dados[2];
                int items = Integer.parseInt(dados[3]);
                double total = Double.parseDouble(dados[4]);
                sales.add(new Sale(month, year, seller, items, total));
                line = br.readLine();
            }

            System.out.println();
            System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
            List<Sale> sales2016 = sales.stream()
                    .filter(sale -> sale.getYear() == 2016)
                    .sorted(Comparator.comparingDouble(Sale::averagePrice).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            for (Sale sale : sales2016) {
                System.out.println(sale);
            }

            double totalLogan = sales.stream()
                    .filter(sale -> sale.getSeller().equals("Logan"))
                    .filter(sale -> sale.getMonth() == 1 || sale.getMonth() == 7)
                    .mapToDouble(Sale::getTotal)
                    .sum();

            System.out.println();
            System.out.printf("Valor total vendido por Logan nos meses 1 e 7: %.2f%n", totalLogan);

        } catch (IOException e) {
            System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
        }


        sc.close();

    }
}