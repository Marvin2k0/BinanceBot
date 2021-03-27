package de.marvinleiers.binancebot;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.CandlestickInterval;

import java.util.Scanner;

/**
    @author Marvin L
    (c) Marvin L., 2019 - 2021 All rights reserved.
 */

class BinanceBot
{
    private BinanceApiClientFactory factory;
    private BinanceApiRestClient client;
    private String input = "";

    BinanceBot()
    {
        factory = BinanceApiClientFactory.newInstance();
        client = factory.newRestClient();
        Utils.setUp(client);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Bitte gebe ein Symbol ein!\n>");
        input = scanner.nextLine().toUpperCase();

        float currentPrice = Float.parseFloat(client.get24HrPriceStatistics(input.toUpperCase()).getLastPrice());
        float simpleMovingAverage = Utils.movingAverage(input.toUpperCase(), CandlestickInterval.ONE_MINUTE, 500);
        float a = currentPrice - simpleMovingAverage;

        if ((a / simpleMovingAverage) >= 0.03)
        {
            System.out.println("Upwards trend detected!");
        } else if ((a / simpleMovingAverage) < -0.016)
        {
            System.out.println("Downwards trend detected!");
        } else
        {
            System.out.println("Sideways trend detected!");
        }

        System.out.println("Current price: " + currentPrice + " MA: " + simpleMovingAverage + " (" + (a / simpleMovingAverage) + ")");
    }

    public BinanceApiRestClient getClient()
    {
        return this.client;
    }
}
