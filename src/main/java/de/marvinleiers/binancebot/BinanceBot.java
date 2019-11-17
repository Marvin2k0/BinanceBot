package de.marvinleiers.binancebot;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;

import java.util.Scanner;

class BinanceBot
{
    BinanceBot() throws InterruptedException
    {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiRestClient client = factory.newRestClient();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Bitte gebe ein Symbol ein!\n>");

        String input = scanner.nextLine();

        while (true)
        {
            System.out.println(client.get24HrPriceStatistics(input).getLastPrice());
            Thread.sleep(1000);
        }
    }
}
