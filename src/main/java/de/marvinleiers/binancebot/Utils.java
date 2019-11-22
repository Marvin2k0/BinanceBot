package de.marvinleiers.binancebot;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import java.util.List;

public class Utils
{
    private static BinanceApiRestClient CLIENT;

    private Utils()
    {
    }

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

        /* TODO */
//    public static float[] calculateResistances(String symbol, CandlestickInterval candlestickInterval, int timeframe)
//    {
//        List<Candlestick> candlesticks = CLIENT.getCandlestickBars(symbol, candlestickInterval)
//                .subList(CLIENT.getCandlestickBars(symbol, candlestickInterval).size() - timeframe - 1,
//                        CLIENT.getCandlestickBars(symbol, candlestickInterval).size() - 1);
//
//        int amount = 0;
//        double percentage = 0.05;
//
//        for (int i = 0; i < candlesticks.size() - 1; i++)
//        {
//            float current = Float.parseFloat(((Candlestick) candlesticks.get(i)).getClose());
//            float next = Float.parseFloat(((Candlestick) candlesticks.get(candlesticks.indexOf(current) + 1)).getClose());
//
//            if (((Math.max(current, next) - Math.min(current, next)) / Math.max(current, next)) <= percentage)
//            {
//                amount++;
//            }
//        }
//
//        System.out.println("amount: " + amount);
//
//        float[] resistances = new float[amount];
//
//        int i = 0;
//        int index = 0;
//
//        while (i < amount)
//        {
//            float current = Float.parseFloat(((Candlestick) candlesticks.get(i)).getClose());
//            float next = Float.parseFloat(((Candlestick) candlesticks.get(candlesticks.indexOf(current) + 1)).getClose());
//
//            if (((Math.max(current, next) - Math.min(current, next)) / Math.max(current, next)) <= percentage)
//            {
//                resistances[index] = (current + next) / 2;
//                System.out.println("idnex: " + index);
//                index++;
//            }
//            System.out.println(i);
//            i++;
//        }
//
//        return resistances;
//    }
}
