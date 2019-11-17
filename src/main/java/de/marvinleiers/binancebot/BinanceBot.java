package de.marvinleiers.binancebot;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import java.util.List;
import java.util.Scanner;

class BinanceBot
{
    BinanceApiClientFactory factory;
    BinanceApiRestClient client;
    String input = "";

    BinanceBot()
    {
        factory = BinanceApiClientFactory.newInstance();
        client = factory.newRestClient();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Bitte gebe ein Symbol ein!\n>");
        input = scanner.nextLine();

        float currentPrice = Float.valueOf(client.get24HrPriceStatistics(input.toUpperCase()).getLastPrice());

        if (currentPrice > calculateSimpleMovingAverage(input.toUpperCase(), 10))
        {
            System.out.println("Upwards trend detected! Current price: " + currentPrice + " MA: " + calculateSimpleMovingAverage(input.toUpperCase(), 10));
        }
        else
        {
            System.out.println("No upwards trend detected!");
        }
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

            toDivide += Float.valueOf(candlestick.getClose());
        }

        return toDivide / amount;
    }
}
