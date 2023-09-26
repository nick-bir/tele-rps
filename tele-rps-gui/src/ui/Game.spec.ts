import { shallowMount } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import Game from './Game.vue';
import RpsSelector from './RpsSelector.vue';
import PlayerHand from './PlayerHand.vue';
import OpponentHand from './OpponentHand.vue';
import Score from './Score.vue';
import { useApi } from '../logic/api';

describe('Game.vue', () => {
    describe('always', () => {
        it('- show score', () => {
            const { isScoreVisible } = setup();
            expect(isScoreVisible()).toBe(true);
        });
    });
    describe('if new round started', () => {
        it('- show rock/paper/scissors selector', () => {
            const { isRpsSelectorVisible } = setup();
            expect(isRpsSelectorVisible()).toBe(true);
        });
        it('- hide hands', () => {
            const { isPlayerHandVisible, isOpponentHandVisible } = setup();
            expect(isPlayerHandVisible()).toBe(false);
            expect(isOpponentHandVisible()).toBe(false);
        });
    });
    describe('if round finished', () => {
        it('- show player hand', () => {
            const { isPlayerHandVisible } = setup({ isRoundFinished: true });
            expect(isPlayerHandVisible()).toBe(true);
        });

        it('- show opponent hand', () => {
            const { isOpponentHandVisible } = setup();
            expect(isOpponentHandVisible()).toBe(true);
        });
    });
});

function setup({ isRoundFinished = false } = {}) {
    if (isRoundFinished) {
        const { finishRound } = useApi();
        finishRound();
    }

    const wrapper = shallowMount(Game);

    const isScoreVisible = () => wrapper.findComponent(Score).exists();
    const isPlayerHandVisible = () =>
        wrapper.findComponent(PlayerHand).exists();
    const isOpponentHandVisible = () =>
        wrapper.findComponent(OpponentHand).exists();
    const isRpsSelectorVisible = () =>
        wrapper.findComponent(RpsSelector).exists();

    return {
        wrapper,
        isScoreVisible,
        isRpsSelectorVisible,
        isPlayerHandVisible,
        isOpponentHandVisible,
    };
}
