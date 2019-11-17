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

        for (Candlestick candlestick : candlesticks)
        {
            System.out.println(candlestick.getClose());
        }
    }
}
