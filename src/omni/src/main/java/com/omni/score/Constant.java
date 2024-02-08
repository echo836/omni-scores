package com.omni.score;
import java.math.BigInteger;
import java.util.List;

public class Constant {

    public static final String TAG = "IRC31";
    static final String NAME="name";
    static final String SYMBOL="symbol";
    static final String TOKEN_URI="token_uri";
    static final String APPROVAL="approval";
    static final String BALANCES="balances";
    static final String ADMIN="admin";
    static final String TOTAL_SUPPLY="total_supply";
    static final String SUPPORTED_NETWORKS="supported_networks";
    static final String PRICES="prices";
    static final String MAIN="main";
    static final String NAME_MAP="name_map";
    static final String NAME_ID="name_ID";
    static final String EXPIRATIONS="expirations";
    static final String OWNERSHIP="ownership";
    static final String COUNT="count";
    static final String BTP="btp_address";
    static final String USER_BALANCE = "user_balance";
    static final String CROSS_CHAIN_BALANCE="cross_chain_balance";
    static final String SERVICE_CONTRACTS="service_contracts";
    static final String AIRDROP="airdrop";

    static final BigInteger ONE_YEAR = BigInteger.valueOf(3_154_000_000_000_0L);
    static final BigInteger GRACE = BigInteger.valueOf(7_776_000_000_000L);
    static final BigInteger PREMIUM_TIME = BigInteger.valueOf(2_592_000_000_000L);
    static final BigInteger PREMIUM_MAX = BigInteger.valueOf(10000);
    static final BigInteger PREMIUM_MIN = BigInteger.valueOf(1);
    static final BigInteger POW18 = BigInteger.valueOf(1_000_000_000_000_000_000L);

    static final List<BigInteger> PRICES_DEFAULT= List.of(
            BigInteger.valueOf(500),
            BigInteger.valueOf(400),
            BigInteger.valueOf(300),
            BigInteger.valueOf(100)
    );

    static final List<String> NETWORKS_DEFAULT= List.of(
            "0x1.icon"
    );

    static final List<String> templates = List.of(
        "<svg id=\"visual\" viewBox=\"0 0 900 900\" width=\"900\" height=\"900\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\"><rect x=\"0\" y=\"0\" width=\"900\" height=\"900\" fill=\"#001220\"></rect><defs><linearGradient id=\"grad1_0\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><defs><linearGradient id=\"grad2_0\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><g transform=\"translate(900, 0)\"><path d=\"M0 445.5C-37.2 433 -74.3 420.5 -108.4 404.7C-142.6 389 -173.7 370 -202 349.9C-230.3 329.8 -256 308.6 -289.2 289.2C-322.5 269.8 -363.3 252.3 -385.8 222.7C-408.3 193.1 -412.3 151.5 -419.2 112.3C-426.1 73.2 -435.8 36.6 -445.5 0L0 0Z\" fill=\"{{color}}\"></path></g><g transform=\"translate(0, 900)\"><path d=\"M0 -445.5C42.2 -448.4 84.4 -451.3 114.1 -426C143.9 -400.7 161.2 -347.1 183.5 -317.8C205.8 -288.5 233 -283.5 275.1 -275.1C317.2 -266.6 374.2 -254.7 385.8 -222.7C397.4 -190.7 363.7 -138.6 366.1 -98.1C368.5 -57.6 407 -28.8 445.5 0L0 0Z\" fill=\"{{color}}\"></path></g><text x=\"50%\" y=\"52.5%\" class=\"base txt\" dominant-baseline=\"middle\" color=\"white\" text-anchor=\"middle\" fill=\"white\" font-size=\"70\" font-weight=\"bold\" font-family=\"arial\" >{{replace}}.omni</text></svg>",
        "<svg id=\"visual\" viewBox=\"0 0 900 900\" width=\"900\" height=\"900\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\"><rect x=\"0\" y=\"0\" width=\"900\" height=\"900\" fill=\"#001220\"></rect><defs><linearGradient id=\"grad1_0\" x1=\"0%\" y1=\"100%\" x2=\"100%\" y2=\"0%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><defs><linearGradient id=\"grad2_0\" x1=\"0%\" y1=\"100%\" x2=\"100%\" y2=\"0%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><g transform=\"translate(900, 900)\"><path d=\"M-445.5 0C-405.1 -29.3 -364.8 -58.6 -350.6 -94C-336.5 -129.3 -348.5 -170.7 -331.7 -191.5C-314.9 -212.3 -269.2 -212.4 -252.4 -252.4C-235.6 -292.5 -247.7 -372.4 -222.7 -385.8C-197.8 -399.2 -135.7 -346 -92.4 -344.8C-49.1 -343.7 -24.5 -394.6 0 -445.5L0 0Z\" fill=\"{{color}}\"></path></g><g transform=\"translate(0, 0)\"><path d=\"M445.5 0C432.6 35.1 419.8 70.2 415.3 111.3C410.9 152.4 414.9 199.6 385.8 222.7C356.7 245.9 294.7 245 257.4 257.4C220.1 269.7 207.6 295.3 188.5 326.5C169.4 357.7 143.8 394.6 111.3 415.3C78.8 436.1 39.4 440.8 0 445.5L0 0Z\" fill=\"{{color}}\"></path></g><text x=\"50%\" y=\"52.5%\" class=\"base txt\" dominant-baseline=\"middle\" color=\"white\" text-anchor=\"middle\" fill=\"white\" font-size=\"70\" font-weight=\"bold\" font-family=\"arial\" >{{replace}}.omni</text></svg>",
        "<svg id=\"visual\" viewBox=\"0 0 900 900\" width=\"900\" height=\"900\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\"><rect x=\"0\" y=\"0\" width=\"900\" height=\"900\" fill=\"#001220\"></rect><defs><linearGradient id=\"grad1_0\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><defs><linearGradient id=\"grad2_0\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><g transform=\"translate(900, 0)\"><path d=\"M0 445.5C-38 442.8 -76 440.2 -115.3 430.3C-154.6 420.4 -195.2 403.3 -212.5 368.1C-229.8 332.8 -223.9 279.4 -256.7 256.7C-289.4 233.9 -360.8 241.8 -376.7 217.5C-392.7 193.2 -353.2 136.8 -355.5 95.2C-357.7 53.6 -401.6 26.8 -445.5 0L0 0Z\" fill=\"{{color}}\"></path></g><g transform=\"translate(0, 900)\"><path d=\"M0 -445.5C34.4 -413.7 68.8 -381.9 96.3 -359.3C123.7 -336.7 144.3 -323.3 186.5 -323C228.7 -322.8 292.7 -335.7 309 -309C325.3 -282.3 294 -216 312.6 -180.5C331.3 -145 399.8 -140.3 430.3 -115.3C460.8 -90.3 453.1 -45.2 445.5 0L0 0Z\" fill=\"{{color}}\"></path></g><text x=\"50%\" y=\"52.5%\" class=\"base txt\" dominant-baseline=\"middle\" color=\"white\" text-anchor=\"middle\" fill=\"white\" font-size=\"70\" font-weight=\"bold\" font-family=\"arial\" >{{replace}}.omni</text></svg>",
        "<svg id=\"visual\" viewBox=\"0 0 900 900\" width=\"900\" height=\"900\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\"><rect x=\"0\" y=\"0\" width=\"900\" height=\"900\" fill=\"#001220\"></rect><defs><linearGradient id=\"grad1_0\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><defs><linearGradient id=\"grad2_0\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><g transform=\"translate(900, 0)\"><path d=\"M0 445.5C-38 442.8 -76 440.2 -115.3 430.3C-154.6 420.4 -195.2 403.3 -212.5 368.1C-229.8 332.8 -223.9 279.4 -256.7 256.7C-289.4 233.9 -360.8 241.8 -376.7 217.5C-392.7 193.2 -353.2 136.8 -355.5 95.2C-357.7 53.6 -401.6 26.8 -445.5 0L0 0Z\" fill=\"{{color}}\"></path></g><g transform=\"translate(0, 900)\"><path d=\"M0 -445.5C34.4 -413.7 68.8 -381.9 96.3 -359.3C123.7 -336.7 144.3 -323.3 186.5 -323C228.7 -322.8 292.7 -335.7 309 -309C325.3 -282.3 294 -216 312.6 -180.5C331.3 -145 399.8 -140.3 430.3 -115.3C460.8 -90.3 453.1 -45.2 445.5 0L0 0Z\" fill=\"{{color}}\"></path></g><text x=\"50%\" y=\"52.5%\" class=\"base txt\" dominant-baseline=\"middle\" color=\"white\" text-anchor=\"middle\" fill=\"white\" font-size=\"70\" font-weight=\"bold\" font-family=\"arial\" >{{replace}}.omni</text></svg>",
        "<svg id=\"visual\" viewBox=\"0 0 900 900\" width=\"900\" height=\"900\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\"><rect x=\"0\" y=\"0\" width=\"900\" height=\"900\" fill=\"#001220\"></rect><defs><linearGradient id=\"grad1_0\" x1=\"0%\" y1=\"100%\" x2=\"100%\" y2=\"0%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><defs><linearGradient id=\"grad2_0\" x1=\"0%\" y1=\"100%\" x2=\"100%\" y2=\"0%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><g transform=\"translate(900, 900)\"><path d=\"M-445.5 0C-420.1 -34 -394.8 -68.1 -381.5 -102.2C-368.3 -136.4 -367.2 -170.7 -360.3 -208C-353.3 -245.3 -340.6 -285.5 -315 -315C-289.4 -344.5 -250.9 -363.1 -212.5 -368.1C-174.1 -373 -135.8 -364.2 -100.4 -374.8C-65 -385.4 -32.5 -415.4 0 -445.5L0 0Z\" fill=\"{{color}}\"></path></g><g transform=\"translate(0, 0)\"><path d=\"M445.5 0C423.4 34.7 401.4 69.3 390.2 104.6C379.1 139.8 378.9 175.7 370.7 214C362.4 252.3 346 293.1 315 315C284 336.9 238.3 339.9 201.5 349C164.7 358.1 136.7 373.2 104.6 390.2C72.5 407.3 36.2 426.4 0 445.5L0 0Z\" fill=\"{{color}}\"></path></g><text x=\"50%\" y=\"52.5%\" class=\"base txt\" dominant-baseline=\"middle\" color=\"white\" text-anchor=\"middle\" fill=\"white\" font-size=\"70\" font-weight=\"bold\" font-family=\"arial\" >{{replace}}.omni</text></svg>",
        "<svg id=\"visual\" viewBox=\"0 0 900 900\" width=\"900\" height=\"900\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\"><rect x=\"0\" y=\"0\" width=\"900\" height=\"900\" fill=\"#001220\"></rect><defs><linearGradient id=\"grad1_0\" x1=\"0%\" y1=\"100%\" x2=\"100%\" y2=\"0%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><defs><linearGradient id=\"grad2_0\" x1=\"0%\" y1=\"100%\" x2=\"100%\" y2=\"0%\"><stop offset=\"30%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop><stop offset=\"70%\" stop-color=\"#001220\" stop-opacity=\"1\"></stop></linearGradient></defs><g transform=\"translate(900, 900)\"><path d=\"M-445.5 0C-407.3 -28.3 -369.1 -56.5 -366.1 -98.1C-363.1 -139.7 -395.2 -194.6 -377.6 -218C-359.9 -241.4 -292.5 -233.3 -263.8 -263.8C-235 -294.2 -244.9 -363.1 -222.7 -385.8C-200.6 -408.5 -146.3 -385.1 -103.8 -387.3C-61.3 -389.6 -30.7 -417.5 0 -445.5L0 0Z\" fill=\"{{color}}\"></path></g><g transform=\"translate(0, 0)\"><path d=\"M445.5 0C423.5 34.8 401.5 69.6 391.2 104.8C380.9 140.1 382.3 175.8 375 216.5C367.6 257.2 351.5 302.8 315 315C278.5 327.2 221.5 306 187.5 324.8C153.5 343.5 142.3 402.1 114.9 428.9C87.5 455.6 43.7 450.6 0 445.5L0 0Z\" fill=\"{{color}}\"></path></g><text x=\"50%\" y=\"52.5%\" class=\"base txt\" dominant-baseline=\"middle\" color=\"white\" text-anchor=\"middle\" fill=\"white\" font-size=\"70\" font-weight=\"bold\" font-family=\"arial\" >{{replace}}.omni</text></svg>"
    );
    static final List<String> colors = List.of(
        "#FBAE3C",
        "#715DF2",
        "#009473",
        "#F7770F",
        "#C62368",
        "#FF6F61"
    );
}
