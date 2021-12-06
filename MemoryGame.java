package MemoryGame;

import java.awt.*;

public class MemoryGame {

    public static final int SIZE_ROW_EASY = 2;
    public static final int SIZE_ROW_MEDIUM= 6;
    public static final int SIZE_ROW_HARD= 8;

    public static final int EASY = 0;
    public static final int MEDIUM= 1;
    public static final int HARD= 2;

    public static final int[] Sizes = {SIZE_ROW_EASY, SIZE_ROW_MEDIUM, SIZE_ROW_HARD};

    public static final int PORT = 7777;

    public static final int NUM_OF_PLAYERS_IN_GAME = 2;

    public static final int TIME_CARD_EXPOSED = 2000;
    public static final int TIME_FOR_MESSAGE = 2000;

    public static final Color[] COLORS= {
        Color.BLUE,
        Color.RED,
        Color.GREEN,
        Color.YELLOW,
        Color.ORANGE,
        Color.MAGENTA,
        Color.PINK,
        new Color(77,41,1) ,
        new Color(50, 20, 20) ,
        new Color(131, 8, 232),
        new Color(246, 158, 114),
        new Color(20, 201, 230),
        new Color(67, 121, 149),
        new Color(90, 200, 50),
        new Color(182, 57, 86),
        new Color(74, 07, 86),
        new Color(62, 93, 10),
        new Color(21, 8, 93),
        new Color(162, 217, 127),
        new Color(69, 179, 217),
        new Color(97, 31, 213),
        new Color(182, 227, 191),
        new Color(107, 218, 119),
        new Color(188, 138, 77),
        new Color(209, 251, 127),
        new Color(44, 136, 207),
        new Color(47, 165, 18),
        new Color(91, 66, 238),
        new Color(46, 156, 221),
        new Color(119, 9, 226),
        new Color(10, 11, 34),
        new Color(155, 253, 129),
        new Color(47, 57, 160),
        new Color(147, 62, 49),
        new Color(168, 255, 105),
        new Color(117, 249, 66),
        new Color(142, 180, 76),
        new Color(154, 223, 77),
        new Color(206, 131, 188),
        new Color(168, 253, 239),
        new Color(171, 109, 61),
        new Color(45, 211, 17),
        new Color(2, 57, 191),
        new Color(25, 48, 199),
        new Color(41, 83, 35),
        new Color(105, 145, 162),
        new Color(138, 244, 8),
        new Color(91, 157, 52),
        new Color(56, 215, 170),
        new Color(25, 118, 212),
        new Color(18, 57, 79),
        new Color(218, 129, 112),
        new Color(222, 224, 1),
        new Color(177, 225, 49),
        new Color(0, 57, 18),
        new Color(191, 248, 169),
        new Color(167, 9, 90),
        new Color(219, 216, 185),
        new Color(0, 89, 179),
        new Color(238, 218, 119),
        new Color(231, 36, 11),
        new Color(87, 183, 137),
        new Color(241, 131, 124),
        new Color(250, 186, 146)
    };

    public static final Color defaultColor = Color.GRAY;
}
