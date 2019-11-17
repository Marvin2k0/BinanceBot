package de.marvinleiers.binancebot;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import java.util.List;
import java.util.Scanner;

/*
    @author Marvin L.
    (c) Marvin L., 2019. All rights reserved.
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

        Scanner scanner = new Scanner(System.in);

        System.out.print("Bitte gebe ein Symbol ein!\n>");
        input = scanner.nextLine();

        float currentPrice = Float.parseFloat(client.get24HrPriceStatistics(input.toUpperCase()).getLastPrice());
        float simpleMovingAverage = calculateSimpleMovingAverage(input.toUpperCase(), 10);
        float a = currentPrice - simpleMovingAverage;

        if ((a / simpleMovingAverage) >= 0.03)
        {
            System.out.println("Upwards trend detected!");
        }
        else if ((a / simpleMovingAverage) < -0.02)
        {
            System.out.println("Downwards trend detected!");
        }
        else
        {
            System.out.println("Sideways trend detected!");
        }

        System.out.println("Current price: " + currentPrice + " MA: " + simpleMovingAverage + " (" + (a / simpleMovingAverage) + ")");
    }

    private float calculateSimpleMovingAverage(String asset, int durationInHours)
    {
        List<Candlestick> candlesticks = client.getCandlestickBars(asset.toUpperCase(), CandlestickInterval.HOURLY);
        List<?> inDuration = candlesticks.subList(candlesticks.size() - durationInHours, candlesticks.size());

        int amount = inDuration.size();
        float toDivide = 0;

        for (Object obj : inDuration)
        {
            Candlestick candlestick = (Candlestick) obj;

            toDivide += Float.parseFloat(candlestick.getClose());
        }

        return toDivide / amount;
    }
}
