import { shallowMount } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import Game from './Game.vue';
import RpsSelector from './RpsSelector.vue';
import PlayerHand from './UserHand.vue';
import OpponentHand from './OpponentHand.vue';
import Score from './Score.vue';
import { useApi } from '../logic/api';

describe('Game.vue', () => {
    describe('always', () => {
        it('- show score', async () => {
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
            const { isUserHandVisible, isOpponentHandVisible } = setup();
            expect(isUserHandVisible()).toBe(false);
            expect(isOpponentHandVisible()).toBe(false);
        });

        describe('when user made choise', () => {
            describe('show hands:', () => {
                it('- user hand', async () => {
                    const { makeChoise, isUserHandVisible } = setup({
                        isRoundStarted: true,
                    });
                    await makeChoise();
                    expect(isUserHandVisible()).toBe(true);
                });
            });
        });
    });
    describe('if round finished', () => {
        it('- show user hand', () => {
            const { isUserHandVisible } = setup({
                isRoundFinished: true,
            });
            expect(isUserHandVisible()).toBe(true);
        });

        it('- show opponent hand', async () => {
            const { isOpponentHandVisible } = setup({
                isRoundFinished: true,
            });
            expect(isOpponentHandVisible()).toBe(true);
        });
    });
});

function setup({ isRoundStarted = false, isRoundFinished = false } = {}) {
    const { startNewGame } = useApi();
    startNewGame();

    if (isRoundStarted) {
        const { startNewRound } = useApi();
        startNewRound();
    }

    if (isRoundFinished) {
        const { finishRound } = useApi();
        finishRound();
    }

    const wrapper = shallowMount(Game);

    const isScoreVisible = () => wrapper.findComponent(Score).exists();
    const isRpsSelectorVisible = () =>
        wrapper.findComponent(RpsSelector).exists();
    const isUserHandVisible = () => wrapper.findComponent(PlayerHand).exists();
    const isOpponentHandVisible = () =>
        wrapper.findComponent(OpponentHand).exists();

    const makeChoise = () => {
        wrapper.findComponent(RpsSelector).vm.$emit('choise', 'rock');
    };

    return {
        wrapper,
        isScoreVisible,
        isRpsSelectorVisible,
        isUserHandVisible,
        isOpponentHandVisible,

        makeChoise,
    };
}
