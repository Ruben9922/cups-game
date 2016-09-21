package uk.co.ruben9922.cupsgame;

import processing.core.PVector;

interface EasingFunction {
    void setVectorComponents(PVector pVector, int time, float beginning, float change, float duration);
}
