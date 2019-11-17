package de.marvinleiers.binancebot;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import java.util.List;
import java.util.Scanner;

class BinanceBot
{
    BinanceBot()
    {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiRestClient client = factory.newRestClient();


        Scanner scanner = new Scanner(System.in);

        System.out.print("Bitte gebe ein Symbol ein!\n>");
        String input = scanner.nextLine();

        List<Candlestick> candlesticks = client.getCandlestickBars(input.toUpperCase(), CandlestickInterval.HOURLY);
        //System.out.println(candlesticks);

        for (Candlestick candlestick : candlesticks)
        {
            System.out.println(candlestick.getClose());
        }

        /*while (true)
        {
            System.out.println(client.get24HrPriceStatistics(input).getLastPrice());
            Thread.sleep(1000);
        }*/
    }

    private float calculateMovingAverage()
    {
        return 0f;
    }
}
