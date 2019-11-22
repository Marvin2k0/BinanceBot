package de.marvinleiers.binancebot;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import java.util.List;

public class Utils
{
    private static BinanceApiRestClient CLIENT;

    private Utils() { }

    public static void setUp(BinanceApiRestClient client)
    {
        CLIENT = client;
    }

    public static float movingAverage(String symbol, CandlestickInterval candlestickInterval, int timeframe)
    {
        List<Candlestick> candlesticks = CLIENT.getCandlestickBars(symbol.toUpperCase(), candlestickInterval);
        List<?> inDuration = candlesticks.subList(candlesticks.size() - timeframe, candlesticks.size());

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
