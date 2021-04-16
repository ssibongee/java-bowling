package bowling.domain.frame;

import bowling.domain.State.PinCount;
import bowling.domain.score.Score;
import bowling.domain.State.State;

public interface Frame {

    void addPinCount(int pinCount);

    void addPinCount(PinCount pinCount);

    boolean isDone();

    Frame nextFrame();

    FrameNumber number();

    boolean isLast();

    State currentState();

    Score score();

    Score calculatedScore(Score scoreToCalculate);

}