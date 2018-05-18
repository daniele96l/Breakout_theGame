package com.mygdx.game.Leaderboard;

public class Score  implements Comparable {
    String name;
    int point;

    public Score(String name, int point) {
        this.name = name;
        this.point = point;

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public int getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }
}

