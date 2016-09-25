package uk.co.ruben9922.cupsgame;

@FunctionalInterface
interface EasingFunction {
    float getValue(int time, float beginning, float change, int duration);
}
