package com.example.gradlesocceronline.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    private static List<String> COUNTRY_CODE = Arrays.asList(
            "VN", "USA", "UK", "CN", "SG", "TW", "CA", "RU", "FR", "GER"
    );

    private static List<String> TEAM_NAME = Arrays.asList(
            "Manchester United", "Liverpool FC", "Arsenal", "Chelsea", "NewCastle",
            "Everton", "Tottenham", "Leicester City", "Leeds United",
            "Full Ham", "Southampton", "Manchester City", "Aston Villa",
            "Wolves", "West Ham", "Crystal Palace",
            "Barcelona", "Real Betis", "Valencia", "Real Madrid", "Real Sociedad",
            "Villarreal", "Atlético Madrid", "Celta Vigo", "Espanyol", "Getafe",
            "Bayern Munich", "Borussia Dortmund", "Schalke 04", "Werder Bremen",
            "Borussia Mönchengladbach", "Hamburger SV", "Lyon", "Monaco", "Paris Saint-Germain",
            "Bordeaux", "Marseille", "Juventus", "Milan", "Roma", "Lazio", "Fiorentina", "Napoli"
    );

    private static List<String> FIRST_NAME = Arrays.asList(
            "Peter", "Tom", "David", "Messi", "Ronaldo", "Frank",
            "Jack", "Barack", "Micheal", "Son", "William", "Harry",
            "Alessandro", "Leonardo", "Cristiano", "Giorgio",
            "Jordan", "Kyle", "Danny", "Eric", "John", "Jesse",
            "Raheem", "Jamie", "Ashley", "Marcus"

    );

    private static List<String> LAST_NAME = Arrays.asList(
            "Smith", "Jones", "Johnson", "Taylor", "Wilson", "Cook",
            "Walker", "Collins", "Thomas", "Harris", "Potter",
            "Florenzi", "Bonucci", "Piccini", "Chiellini",
            "Pickford", "Rose", "Dier", "Stones", "Maguire",
            "Lingard", "Henderson", "Kane", "Sterling", "Vardy",
            "Young", "Rashford"

    );


    public static String randomCountryCode() {
        Random rand = new Random();
        int index = rand.nextInt(COUNTRY_CODE.size());// 0 <= index <= size-1
        return COUNTRY_CODE.get(index);
    }

    public static String randomTeamName() {
        Random rand = new Random();
        int index = rand.nextInt(TEAM_NAME.size());// 0 <= index <= size-1
        return TEAM_NAME.get(index);
    }

    public static String randomFirstName() {
        Random rand = new Random();
        int index = rand.nextInt(FIRST_NAME.size());// 0 <= index <= size-1
        return FIRST_NAME.get(index);
    }

    public static String randomLastName() {
        Random rand = new Random();
        int index = rand.nextInt(LAST_NAME.size());// 0 <= index <= size-1
        return LAST_NAME.get(index);
    }

    public static int random(int minlen, int maxlen) {
        Random rand = new Random();
        int value = rand.nextInt((maxlen - minlen) + 1) + minlen;
        return value;
    }
}
