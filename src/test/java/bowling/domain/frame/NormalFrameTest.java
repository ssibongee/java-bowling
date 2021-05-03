package bowling.domain.frame;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bowling.domain.score.Score;
import bowling.domain.state.BowlingPin;

public class NormalFrameTest {

    @Test
    void 생성_테스트() {
        // given
        Frame normalFrame = NormalFrame.init();
        // when & then
        assertThat(normalFrame).isInstanceOf(NormalFrame.class);
        assertThatCode(() -> NormalFrame.init()).doesNotThrowAnyException();
    }

    @Test
    void 현재_프레임_진행중_테스트() {
        // given
        Frame normalFrame = NormalFrame.init();
        // when
        normalFrame.bowl(BowlingPin.of(5));
        // then
        assertThat(normalFrame.isDone()).isFalse();
    }

    @Test
    void 현재_프레임_완료_테스트() {
        // given
        Frame normalFrame = NormalFrame.init();
        // when
        normalFrame.bowl(BowlingPin.of(5));
        normalFrame.bowl(BowlingPin.of(5));
        // then
        assertThat(normalFrame.isDone()).isTrue();
    }

    @Test
    void 점수_반환_테스트() {
        // given
        Frame normalFrame = NormalFrame.init();
        // when
        normalFrame.bowl(BowlingPin.of(5));
        normalFrame.bowl(BowlingPin.of(3));
        // then
        assertThat(normalFrame.score()).isEqualTo(Score.of(8));
    }

    @Test
    void 점수_반환_추가_점수_테스트() {
        // given
        Frame normalFrame = NormalFrame.init();
        // when
        normalFrame.bowl(BowlingPin.of(5));
        normalFrame.bowl(BowlingPin.of(5));
        // then
        assertThat(normalFrame.score()).isEqualTo(Score.ofProgress());
    }
}