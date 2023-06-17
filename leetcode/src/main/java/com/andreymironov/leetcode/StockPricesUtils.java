package com.andreymironov.leetcode;

public class StockPricesUtils {
    /**
     * @param prices array, prices[i] is the price of a given stock at the ith day
     * @return maximum profit, if you buy 1 item of stock and then later sell it, or 0, if no profit can be achieved
     */
    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < prices.length; i++) {
            int current = prices[i];
            if (current < min) {
                min = current;
            } else if (current - min > maxProfit) {
                maxProfit = current - min;
            }
        }

        return maxProfit;
    }

    /**
     * @param prices array, prices[i] is the price of a given stock at the ith day
     * @return maximum profit, if you can buy and then sell at most 1 item of stock multiple times, or 0, if no profit can be achieved
     */
    public static int maxProfitMultiple(int[] prices) {
        int maxProfit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int current = prices[i];
            int next = prices[i + 1];
            if (current < next) {
                maxProfit += next - current;
            }
        }

        return maxProfit;
    }

    /**
     * @param prices array, prices[i] is the price of a given stock at the ith day
     * @param fee    transaction fee, need to be payed on selling
     * @return maximum profit, if you can buy and then sell at most 1 item of stock multiple times, or 0, if no profit can be achieved
     */
    public static int maxProfitMultipleWithFee(int[] prices, int fee) {
        int n = prices.length;

        if (n == 0) {
            return 0;
        }

        int maxProfitWithoutStock = 0, maxProfitWithStock = -prices[0];

        for (int i = 1; i < n; i++) {
            int currentPrice = prices[i];
            maxProfitWithoutStock = Math.max(maxProfitWithoutStock, maxProfitWithStock + currentPrice - fee);
            maxProfitWithStock = Math.max(maxProfitWithStock, maxProfitWithoutStock - currentPrice);
        }

        return maxProfitWithoutStock;
    }

    /**
     * @param prices array, prices[i] is the price of a given stock at the ith day
     * @return max profit, if you can buy and sell 1 stock multiple times, and after selling you need to cool down for a 1 day
     */
    public static int maxProfitWithCooldown(int[] prices) {
        int n = prices.length;
        // maxProfit[i] is the max profit which can be reached up to i-th day
        int[] maxProfit = new int[n];

        for (int i = 1; i < n; i++) {
            int maxProfitIfSellOnTheDayI = 0;

            for (int j = 0; j < i; j++) {
                int maxProfitIfBuyOnTheDayJ = j < 2
                        ? prices[i] - prices[j]
                        : maxProfit[j - 2] + prices[i] - prices[j];
                maxProfitIfSellOnTheDayI = Math.max(maxProfitIfSellOnTheDayI, maxProfitIfBuyOnTheDayJ);
            }

            int maxProfitIfCooldownOnTheDayI = maxProfit[i - 1];

            maxProfit[i] = Math.max(maxProfitIfCooldownOnTheDayI, maxProfitIfSellOnTheDayI);
        }

        return maxProfit[n - 1];
    }
}
